package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.FrontEnd.GlobalScope;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.FrontEnd.Scope;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.NodeFamily.IRNode.*;
import simplespy.compiler2017.Utils.AsmUtils;
import simplespy.compiler2017.Utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by spy on 5/18/17.
 */
public class CodeGenerator implements IRVisitor {
    public  AssemblyCode ac;
    private Map<String, StringLiteralNode> stringPool;
    int numofGlobalString;
    GlobalScope gl;
    IRRoot ir;
    private int rsp = 0;
    private boolean flag = false;

    public AssemblyCode getAC() {
        return ac;
    }

    @Override
    public void visit(IRRoot node) {
        stringPool = node.typeTable.getStringMap();
        gl = node.scope;
        ir = node;
        locateSymbols(node);
        ac = new AssemblyCode();
        if (node.vars != null){
            numofGlobalString = 0;
            generateDataSection(ac, node.vars);
        }
        if (!stringPool.isEmpty() && numofGlobalString != stringPool.keySet().size()){
            generateReadOnlyDataSection(ac);
        }
        generateTextSection(ac, node.funcs);
    }

    private void locateSymbols(IRRoot ir) {//global variables
        for (VarDecNode var : ir.vars){
            Symbol sym = new Symbol("var@"+var.getName());
            var.setAddress(sym);
            var.setMemoryReference(new DirectMemoryReference(sym));
        }
    }

    private void generateDataSection(AssemblyCode file, List<VarDecNode> gvars){
        file._data();
        file.label("fmtd");
        file.define(new ImmediateValue(0),new Symbol("\"%ld\""));
        file.label("fmts");
        file.define(new ImmediateValue(0),new Symbol("\"%s\""));
        gvars.stream().filter(x->x.init != null && !(x.init instanceof NullLiteralNode)).forEachOrdered(x ->{
            Symbol sym = file.label("var@"+x.getName());
            x.setAddress(sym );
            x.setMemoryReference(new DirectMemoryReference(sym));
            initialize(x);
        });
        file._bss();
        gvars.stream().filter(x->x.init == null).forEachOrdered(x -> file.addBss("var@"+x.getName()+"\tresq \t1"));
    }

    private void initialize(VarDecNode node){
        if (node.ir instanceof Int){
            ac.define(new ImmediateValue(((Int) node.ir).getValue()));
        }else if (node.ir instanceof Str){
            ac.define(new Symbol('`'+((Str) node.ir).getValue()+'`'));
            stringPool.get(((Str) node.ir).getValue()).isGlobal = true;
            numofGlobalString++;
        }else if (node.ir instanceof Malloc) {
            ac.define(new ImmediateValue(0));
            ir.funcs.get("main").getIr().add(0, new Assign(node.getLoc(), new Addr(node), node.ir));

        }else if (node.ir instanceof Bin){
            ac.define(new ImmediateValue(calculate((Bin) node.ir)));

        }
         else{
                throw new Error("unknown Initializer");
        }
    }

    private int calculate(Bin node){
        if (node.getLeft() instanceof Int && node.getRight() instanceof Int){
            int left = ((Int) node.getLeft()).getValue();
            int right = ((Int) node.getRight()).getValue();
            switch (node.getOp()) {
                case ADD:   return left + right;
                case SUB:   return left - right;
                case MUL:   return left * right;
                case DIV:   return left / right;
                case MOD:   return left % right;
                case BITWISE_AND:   return left & right;
                case BITWISE_OR:    return left | right;
                case XOR:   return left ^ right;
                case SHL:   return left >> right;
                case SHR:   return left << right;
            }
        }else if (node.getLeft() instanceof Int && node.getRight() instanceof Bin){
        }else{
            throw new Error("constant");
        }
        return 0;
    }

    private void generateReadOnlyDataSection(AssemblyCode file){
        file._rodata();
        stringPool.keySet().stream().filter(name -> stringPool.get(name).isGlobal == false).forEachOrdered(name -> {
            Label label = new Label();
            file.label(label);
            Symbol symbol = new Symbol('`'+name+'`');
            stringPool.get(name).setAddress(label.getSymbol());
            stringPool.get(name).setMemoryReference(new DirectMemoryReference(label.getSymbol()));
            file.define(new ImmediateValue(0),symbol);
        });
    }
    private void generateTextSection(AssemblyCode file, Map<String, FuncDefNode> funcs){
        file._text();
        funcs.keySet().stream().forEachOrdered(x->{
            file.label(x);
            compileFunctionBody(file, funcs.get(x));
        });
        ir.typeTable.getClassTypeMap().keySet().stream().forEachOrdered(x->{
            String base = x;
            ClassDefNode cd = ir.typeTable.getClassDefNode(x);
            cd.funcs.keySet().stream().forEachOrdered(y->{
                FuncDefNode func = cd.funcs.get(y);
                file.label(base+'@'+y);
                compileFunctionBody(file, func);
            });
        });
    }

    private void compileFunctionBody(AssemblyCode file, FuncDefNode func){
        StackFrame frame = new StackFrame();
        locateParameters(func.parameters);
        frame.lvarSize = locateLocalVars(func.scope);
        AssemblyCode body = optimize(compileStmts(func));
        frame.saveRegs = usedCalleeSaveRegisters(body);
        frame.tempSize = body.virtualStack.getMax();

        fixLocalVariableOffsets(func.scope, frame.lvarOffset());
        fixTempVariableOffsets(body, frame.tempOffset());

        generateFunctionBody(file, body, frame);

    }
    static final private int PARAM_START_WORD = 2;
    static final public int STACK_WORD_SIZE = 8;

    private void locateParameters(List<VarDecNode> paras){
        int numWords = PARAM_START_WORD;
        int i = 0;
        for(VarDecNode var : paras){
            /*if (i < PARAS_REG.length){
                var.setMemoryReference(new IndirectMemoryReference(0, PARAS_REG[i]));
                ++i;
            }else {*/
                var.setMemoryReference(new IndirectMemoryReference(numWords * STACK_WORD_SIZE, bp()));
                ++numWords;
            //}
        }
    }

    private AssemblyCode acfunc;
    private Label epilogue;
    private AssemblyCode compileStmts(FuncDefNode func){
        acfunc = new AssemblyCode();
        epilogue = new Label();
        rsp = 0;
        func.ir.stream().forEachOrdered(this::visit);
        acfunc.label(epilogue);
        return acfunc;
    }

    private List<Register> calleeSaveRegistersCache = null;

    private List<Register> calleeSaveRegisters() {
        if (calleeSaveRegistersCache == null) {
            List<Register> regs = new ArrayList<>();
            for (Register.RegisterClass c : CALLEE_SAVE_REGISTERS) {
                regs.add(new Register(c));
            }
            calleeSaveRegistersCache = regs;
        }
        return calleeSaveRegistersCache;
    }
    private List<Register> usedCalleeSaveRegisters(AssemblyCode body) {
        List<Register> result = calleeSaveRegisters().stream().filter(reg -> body.doesUses(reg)).collect(Collectors.toList());
        result.remove(bp());
        return result;
    }

    private void fixLocalVariableOffsets(Scope scope, int len){
        for (Node var : scope.getEntities().values()){
            if (var instanceof VarDecInBlockNode){
                 var.getVardec().getMemoryReference().fixOffset(-len);
            }
            else if (var instanceof ConstructorNode) continue;
            else var.getMemoryReference().fixOffset(-len);
        }
    }

    private void fixTempVariableOffsets(AssemblyCode asm, int len){
        asm.virtualStack.fixOffset(-len);
    }

    private void generateFunctionBody(AssemblyCode file, AssemblyCode body, StackFrame frame){
        file.virtualStack.reset();
        prologue(file, frame.saveRegs, frame.frameSize());
        boolean printornot = (frame.frameSize() % 16 == 0);
        file.addAll(body.getAssemblies(), printornot);
        file.addExtern(body.getExterns());
        epilogue(file, frame.saveRegs);
        file.virtualStack.fixOffset(0);

    }

    private void prologue(AssemblyCode file, List<Register> saveRegs, int frameSize){
        file.push(bp());
        file.mov(sp(), bp());
        saveRegs.stream().forEachOrdered(x->file.virtualPush(x));
        if (frameSize > 0) file.sub(new ImmediateValue(frameSize), sp());
    }

    private void epilogue(AssemblyCode file, List<Register> saveRegs){
        ListUtils.reverse(saveRegs).stream().forEachOrdered(x->file.virtualPop(x));
        file.mov(bp(),sp());
        file.pop(bp());
        file.ret();
    }

    @Override
    public void visit(ExprStmt node) {
        visit(node.getExpr());
    }

    @Override
    public void visit(Bin node) {
        Node entity = node.getEntityForce();
        if (entity != null && entity.getType() instanceof BaseType && entity.getType().toString().equals("STRING")){
            compileStringOp(node);
        }
        else if (node.isString()){
            compileStringOp(node);

        }
        else{
            visit(node.getRight());
            acfunc.virtualPush(ax());
            visit(node.getLeft());
            acfunc.virtualPop(cx());
            compileBinaryOp(node.getOp(), ax(), cx());
        }
    }

    private void compileBinaryOp(BinaryOpNode.BinaryOp op, Register left, Operand right){
        switch (op) {
            case ADD:
                acfunc.add(right, left);
                break;
            case SUB:
                acfunc.sub(right, left);
                break;
            case MUL:
                acfunc.mul(right, left);
                break;
            case DIV:
            case MOD:
                acfunc.mov(new ImmediateValue(0),dx());
                acfunc.mov(left, ax());
                acfunc.mov(right, cx());
                acfunc.div(cx());
                if (op.equals(BinaryOpNode.BinaryOp.MOD)) {
                    acfunc.mov(dx(), left);
                }
                break;
            case BITWISE_AND:
                acfunc.and(right, left);
                break;
            case BITWISE_OR:
                acfunc.or(right, left);
                break;
            case XOR:
                acfunc.xor(right, left);
                break;
            case SHL:
                acfunc.sal(cl(), left);
                break;
            case SHR:
                acfunc.shr(cl(), left);
                break;
            default:
                // Comparison operators
                acfunc.cmp(right, ax());
                switch (op) {
                    case EQ:    acfunc.sete (al()); break;
                    case NE:    acfunc.setne(al()); break;
                    case GT:    acfunc.setg (al()); break;
                    case GE:    acfunc.setge(al()); break;
                    case LT:    acfunc.setl (al()); break;
                    case LE:    acfunc.setle(al()); break;
                    default:
                        throw new Error("unknown binary operator: " + op);
                }
                acfunc.movzx(al(), left);
        }
    }

    private void compileStringOp(Bin node){
        //ax cx
        if (node.getOp().equals(BinaryOpNode.BinaryOp.ADD)){
            Symbol strlen = new Symbol("strlen");
            ac.addExtern(strlen);

            acfunc.virtualPush(r15());//length of left
            acfunc.virtualPush(r14());
            acfunc.virtualPush(r13());
            rsp += 8;

            visit(node.getLeft());
            acfunc.mov(ax(),r14());//r14 = left
            acfunc.mov(ax(),di());
            call(strlen);
            acfunc.mov(ax(),r15());

            visit(node.getRight());
            acfunc.mov(ax(),r13());//r13 = right
            acfunc.mov(ax(),di());//
            call(strlen);
            acfunc.add(r15(),ax());//new length

            Symbol buffer = new Symbol(varBase + varnum++);
            ac.addBss(buffer.name+':' + "\tresq  1" );

            Symbol malloc = new Symbol("malloc");
            acfunc.addExtern(malloc);
            acfunc.mov(ax(),di());
            acfunc.inc(di());

            call(malloc);
            acfunc.mov(ax(),new DirectMemoryReference(buffer));//new Address

            Symbol strcpy = new Symbol("strcpy");
            ac.addExtern(strcpy);
            acfunc.mov(r14(), si());
            acfunc.mov(ax(),di());
            call(strcpy);
            acfunc.mov(r13(), si());
            acfunc.mov(new DirectMemoryReference(buffer), ax());
            acfunc.add(r15(), ax());//add length
            acfunc.mov(ax(),di());
            call(strcpy);

            acfunc.mov(new DirectMemoryReference(buffer), ax());
            acfunc.virtualPop(r13());//length of left
            acfunc.virtualPop(r14());
            acfunc.virtualPop(r15());
            rsp -= 8;
        }
        else {
            Symbol strcmp = new Symbol("strcmp");
            ac.addExtern(strcmp);
            call(strcmp);
            acfunc.cmp(new ImmediateValue(0), eax());
            acfunc.mov(new ImmediateValue(0), ax());
            switch (node.getOp()) {
                case EQ:    acfunc.sete (al()); break;
                case NE:    acfunc.setne(al()); break;
                case GT:    acfunc.setg (al()); break;
                case GE:    acfunc.setge(al()); break;
                case LT:    acfunc.setl (al()); break;
                case LE:    acfunc.setle(al()); break;
                default:
                    throw new Error("unknown binary operator: ");

            }
        }


    }

    static int varnum = 0;
    static String varBase = "var";
    private Register[] PARAS_REG = {di(), si(), dx(), cx(), r8(), r9()};

    @Override
    public void visit(Call node) {
        String funcName = node.getEntityForce().getName();
        Node entity = node.getEntityForce();
        Symbol malloc = new Symbol("malloc");

        if (funcName.equals("getInt") && entity.equals(gl.get(funcName))){
            Symbol var = new Symbol(varBase + varnum++);
            ac.addBss(var.name+':' + "\tresq  1" );
            acfunc.mov(var, si());
            acfunc.mov(new Symbol("fmtd"), di());
            call(new Symbol(transFuncName(funcName)));
            acfunc.mov(new DirectMemoryReference(var),ax());

        }
        else if (funcName.equals("getString") && entity.equals(gl.get(funcName))){
            ac.addExtern(malloc);
            acfunc.push(r15()); rsp += 8;
            acfunc.mov(new ImmediateValue(300), di());
            call(malloc);
            acfunc.mov(ax(), r15());
            acfunc.mov(new Symbol("fmts"), di());
            acfunc.mov(r15(),si());
            call(new Symbol(transFuncName(funcName)));
            acfunc.mov(r15(),ax());
            acfunc.pop(r15());rsp -= 8;
        }
        else if (funcName.equals("toString") && entity.equals(gl.get(funcName))){
            Symbol var = new Symbol(varBase + varnum++);
            ac.addExtern(malloc);
            ac.addBss(var.name+':' + "\tresq  1" );

            acfunc.mov(new ImmediateValue(20), di());
            call(malloc);
            acfunc.mov(ax(),new DirectMemoryReference(var));//new Address

            visit(node.getArgs().get(0));
            acfunc.mov(ax(), dx());
            acfunc.mov(new Symbol("fmtd"), si());
            acfunc.mov(new DirectMemoryReference(var), di());
          //    acfunc.mov(var, di());


            call(new Symbol(transFuncName(funcName)));//call sprintf
            acfunc.mov(new DirectMemoryReference(var), ax());
         //   acfunc.mov(var, ax());


            /*
             acfunc.virtualPush(ax());
             acfunc.mov(new ImmediateValue(20), di());
            call(malloc);
            acfunc.mov(ax(),new DirectMemoryReference(var));//new Address
            acfunc.virtualPop(dx());
            acfunc.mov(new Symbol("fmtd"), si());
            acfunc.mov(ax(), di());
            call(new Symbol(transFuncName(funcName)));
            acfunc.mov(new DirectMemoryReference(var), ax());
            /*
             */


   /*        ac.addExtern(malloc);
            visit(node.getArgs().get(0));
            acfunc.virtualPush(ax());
            acfunc.mov(new ImmediateValue(20), di());
            call(malloc);
            acfunc.mov(ax(), di());
            acfunc.mov(new Symbol("fmtd"), si());
            acfunc.virtualPop(dx());
            acfunc.virtualPush(ax());
            call(new Symbol(transFuncName(funcName)));
            acfunc.virtualPop(di());
            acfunc.mov(ax(),new IndirectMemoryReference(0,di()));*/
        }
        else if (funcName.equals("length") && entity.equals(gl.string.get(funcName))){
            visit(node.argThis);
            acfunc.mov(ax(),di());
            call(new Symbol(transFuncName(funcName)));

        }else if (funcName.equals("println") && entity.equals(gl.get(funcName))){
            visit(node.getArgs().get(0));
            acfunc.mov(ax(), di());
            call(new Symbol(transFuncName(funcName)));
        }else if (funcName.equals("print") && entity.equals(gl.get(funcName))){
            visit(node.getArgs().get(0));
            acfunc.mov(ax(), di());
            call(new Symbol(transFuncName(funcName)));
        }
        else if (funcName.equals("ord") && entity.equals(gl.string.get(funcName))){
            if (node.argThis != null){
                visit(node.argThis);
                acfunc.virtualPush(ax()); //first char
                visit(node.getArgs().get(0));
                acfunc.mov(ax(),di());//arg
                acfunc.virtualPop(cx());
                acfunc.add(di(),cx());
                acfunc.movsx(new Symbol("byte [rcx]"), ax());




            }
        } else if (funcName.equals("size") && entity.equals(gl.array.get(funcName))){
            if (node.argThis != null){
                visit(node.argThis);
                acfunc.mov(new IndirectMemoryReference(0,ax()), ax());
            }
        } else if (funcName.equals("parseInt") && entity.equals(gl.string.get(funcName))) {
            Symbol sscanf = new Symbol("sscanf");
            ac.addExtern(sscanf);
            Symbol var = new Symbol(varBase + varnum++);
            ac.addBss(var.name+':' + "\tresq  1" );

            visit(node.argThis);
            acfunc.mov(ax(), di());
            acfunc.mov(new Symbol("fmtd"), si());
            acfunc.mov(var, dx());
            call(sscanf);
            acfunc.mov(new DirectMemoryReference(var), ax());


        }
        else if (funcName.equals("substring") && entity.equals(gl.string.get(funcName))) {
            Symbol  memcpy = new Symbol("memcpy");
            ac.addExtern(memcpy);
            ac.addExtern(malloc);
            acfunc.virtualPush(r15());
            acfunc.virtualPush(r14());
            visit(node.argThis);
            acfunc.mov(ax(), di());//address
            visit(node.getArgs().get(0));
            acfunc.mov(ax(),si());//int left
            visit(node.getArgs().get(1));
            acfunc.mov(ax(),dx());//int right

            acfunc.mov(di(),r15());
            acfunc.add(si(),r15());//address
            acfunc.mov(dx(),r14());
            acfunc.inc( r14());
            acfunc.sub(si(),r14());//length

            acfunc.mov(r14(),di());
            call(malloc);

            acfunc.mov(ax(),di());//new Address
            acfunc.mov(r15(),si());
            acfunc.mov(r14(),dx());
            call(memcpy);

            acfunc.mov(ax(),r15());
            acfunc.add(r14(),r15());
            acfunc.xor(r15(),r15());
            acfunc.virtualPop(r14());
            acfunc.virtualPop(r15());
        }
        else if (entity instanceof FuncDefNode){
            if (((FuncDefNode) entity).externClass != null) {
                ClassDefNode classDefNode = ((FuncDefNode) entity).externClass;

                if (node.argThis != null){
                    visit(node.argThis);
                    acfunc.virtualPush(ax());
                }

                rsp += node.getArgs().size() * STACK_WORD_SIZE;
                if (rsp % 16 != 0) {
                    acfunc.align(0, true);
                    rsp += 8;
                    flag = true;
                } else {
                    acfunc.align(0, false);
                }
                for (Expr arg : ListUtils.reverse(node.getArgs())) {
                    visit(arg);
                    acfunc.push(ax());
                }
                if (node.argThis != null) {
                    acfunc.virtualPop(ax());
                    acfunc.virtualPush(r13());
                    acfunc.mov(ax(), r13());
                }
                acfunc.call(new Symbol(classDefNode.name + "@" + funcName));
                if (node.argThis != null){ acfunc.virtualPop(r13());}
                if (flag) {
                    acfunc.align(1, true);
                    rsp -= 8;
                    flag = false;
                } else {
                    acfunc.align(1, false);
                }
                acfunc.add(new ImmediateValue(STACK_WORD_SIZE * node.getArgs().size()), sp());
                rsp -= node.getArgs().size() * STACK_WORD_SIZE;
            }
            else{
                int i = 0;
                rsp += node.getArgs().size() * STACK_WORD_SIZE;
                if (rsp % 16 != 0){
                    acfunc.align(0,true);
                    rsp += 8;
                    flag = true;
                }else{acfunc.align(0,false);}
                for (Expr arg: ListUtils.reverse(node.getArgs())){
                    visit(arg);
                    acfunc.push(ax());

               /* compileExpr(arg, PARAS_REG[node.getArgs().size()-1-i]);
                if(i >= PARAS_REG.length) throw new Error("more than 6 paras");
                ++i;*/
                }
                acfunc.call(new Symbol(transFuncName(funcName)));
                if (flag){
                    acfunc.align(1,true);
                    rsp -= 8;
                    flag = false;
                }else{acfunc.align(1,false);}
                acfunc.add(new ImmediateValue(STACK_WORD_SIZE*node.getArgs().size()), sp());
                rsp -= node.getArgs().size() * STACK_WORD_SIZE;
            }
        }

    }

    private String transFuncName(String name){
        String externName;
        if (name.equals("println")) externName = "puts";
        else if (name.equals("print")) externName = "printf";
        else if (name.equals("getString") || name.equals("getInt")) externName = "scanf";
        else if (name.equals("toString")) externName = "sprintf";
        else if (name.equals("length")) externName = "strlen";
        else return name;
        acfunc.addExtern(new Symbol(externName));
        return externName;
    }

    @Override
    public void visit(CJump node) {
        if(node.getCond() == null) acfunc.mov(new ImmediateValue(1), ax());
        else visit(node.getCond());
        acfunc.test(ax(),ax());
        acfunc.jnz(node.getThen());
        acfunc.jmp(node.getOtherwise());
    }

    @Override
    public void visit(Expr node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(Int node) {
        acfunc.mov(new ImmediateValue(node.getValue()), ax());
    }

    @Override
    public void visit(Jump node) {
        acfunc.jmp(node.getLabel());
    }

    @Override
    public void visit(LabelStmt node) {acfunc.label(node.label);}

    @Override
    public void visit(Return node) {
        if (node.getExpr() != null){
            node.getExpr().accept(this);
        }
        acfunc.jmp(epilogue);
    }

    @Override
    public void visit(Str node) {
        StringLiteralNode ent = stringPool.get(node.getValue());
        acfunc.mov(ent.getAddress(), ax());
    }

    @Override
    public void visit(Uni node) {
        visit(node.getExpr());
        switch (node.getOp()){
            case NEG:
                acfunc.neg(ax());
                break;
            case LOGICAL_NOT:
                acfunc.test(ax(),ax());
                acfunc.sete(al());
                acfunc.movzx(al(),ax());
                break;
            case BITWISE_NOT:
                acfunc.not(ax());
                break;
            default:
        }
    }

    @Override
    public void visit(Var node) {
        acfunc.mov(node.getMemoryReference(), ax());
    }

    @Override
    public void visit(VarDecNode node) {}

    @Override
    public void visit(Assign node) {
        if (node.getRhs() == null) return;

        if(node.getLhs().isAddr() && (node.getLhs().getMemoryReference() != null)){
            visit(node.getRhs());
            acfunc.mov(ax(), (node.getLhs().getMemoryReference()));
        }
        else if (node.getRhs().isConstant()){
            visit(node.getLhs());
            acfunc.mov(ax(),cx());
            loadConstant(node.getRhs(), ax());
            acfunc.mov(ax(), new IndirectMemoryReference(0,cx()));
        }
        else{
            visit(node.getRhs());
            acfunc.virtualPush(ax());
            visit(node.getLhs());
            acfunc.mov(ax(),cx());
            acfunc.virtualPop(ax());
            acfunc.mov(ax(), new IndirectMemoryReference(0,cx()));
        }
    }

    private void loadConstant(Expr node, Register reg) {
        if (node.asmValue() != null) {
            acfunc.mov(node.asmValue(), reg);
        }
        else if (node.getMemoryReference() != null) {
            acfunc.lea(node.getMemoryReference(), reg);
        }
        else {
            throw new Error("must not happen: constant has no asm value");
        }
    }

    @Override
    public void visit(FuncDefNode node) {}

    @Override
    public void visit(Stmt node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(Addr node) {
        Node entity = node.entity;
        if (node.getAddress() != null){
            acfunc.mov(new DirectMemoryReference(node.getAddress()), ax());
        }
        else if(entity.getType() instanceof ArrayType || entity.getType() instanceof ClassType){
            acfunc.mov(node.getMemoryReference(),ax());
        }
        else{
            acfunc.lea(node.getMemoryReference(),ax());
        }
    }

    @Override
    public void visit(Mem node) {
        visit(node.expr);
        acfunc.mov(new IndirectMemoryReference(0, ax()), ax());
    }

    @Override
    public void visit(Malloc node) {
        if (acfunc == null) acfunc = ac;
        Symbol malloc = new Symbol("malloc");
        acfunc.addExtern(malloc);

        if (node.dimList.size() < 1) {
            visit(node.spaceSize);
            acfunc.mov(ax(), di());

            call(malloc);

            if (node.arraySize != null) {
                acfunc.virtualPush(ax());
                visit(node.arraySize);
                acfunc.virtualPop(cx());
                acfunc.mov(ax(), new IndirectMemoryReference(0, cx()));
                acfunc.mov(cx(), ax());
            }
            if (node.getEntity().getType() instanceof ClassType) {
                ClassDefNode cls = ir.typeTable.getClassDefNode(node.getEntity().getType().toString());
                if (cls.constructor != null) {
                    acfunc.virtualPush(ax());
                    acfunc.virtualPush(r13());
                    acfunc.mov(ax(), r13());
                    call(new Symbol(cls.name + '@' + cls.name));
                    acfunc.virtualPop(r13());
                    acfunc.virtualPop(ax());
                }
            }
        }
        else {
            acfunc.push(r15());
            acfunc.push(r14());
            HeapAllocate(node.dimList, node);
            acfunc.pop(r14());
            acfunc.pop(r15());        }



    }
    static int mallocnum = 0;

    void HeapAllocate(List<Expr> dimList, Malloc node){
        if (acfunc == null) acfunc = ac;
        Symbol malloc = new Symbol("malloc");
        acfunc.addExtern(malloc);

        //new int[3][4][5] dimList = {4,5,6}

            Expr dimSize = dimList.get(0);//dimSize = 4
            visit(dimSize);//dimsize reserved in ax
            acfunc.virtualPush(ax());
            acfunc.mul(new ImmediateValue(8), ax());//ax = 32
            acfunc.mov(ax(),di());
            call(malloc);//new address reserved in ax

            acfunc.virtualPop(cx());//dimsize reserved in cx
            acfunc.dec(cx());
            acfunc.mov(cx(), new IndirectMemoryReference(0,ax()));//save size info
            acfunc.inc(cx());
        if (dimList.size() == 1) {
            if (node.getEntity().getType().getBaseType() instanceof ClassType){
                ClassDefNode cls = ir.typeTable.getClassDefNode(node.getEntity().getType().getBaseType().toString());

                acfunc.virtualPush(ax());

                acfunc.mov(ax(),r14());//head address resetved in r14
                acfunc.xor(r15(), r15());//r15 = i
                Label beginLable = new Label("Malloc@Begin_"+cls.name+(mallocnum++));
                Label thenLabel = new Label("Malloc@Then_"+cls.name+(mallocnum++));
                Label elseLabel = new Label("Malloc@Else_"+cls.name+(mallocnum++));
                //r15 from offset 0
                //cmp r15 and size
                acfunc.label(beginLable);
                acfunc.mov(r15(),ax());
                acfunc.cmp(cx(),ax());
                acfunc.setl(al());
                acfunc.movzx(al(),ax());
                acfunc.test(ax(),ax());
                acfunc.jnz(thenLabel);
                acfunc.jmp(elseLabel);
                //if ax < size
                acfunc.label(thenLabel);
                acfunc.inc(r15());//++i
                acfunc.mov(r15(),ax());
                acfunc.mul(new ImmediateValue(8), ax());
                acfunc.virtualPush(r14());
                acfunc.add(ax(),r14());
                acfunc.mov(new ImmediateValue(node.baseSize), di());
                acfunc.virtualPush(cx());
                call(malloc);
                acfunc.virtualPop(cx());

                if (cls.constructor != null) {
                    acfunc.virtualPush(ax());
                    acfunc.virtualPush(r13());
                    acfunc.mov(ax(), r13());
                    call(new Symbol(cls.name + '@' + cls.name));
                    acfunc.virtualPop(r13());
                    acfunc.virtualPop(ax());
                }
                acfunc.mov(ax(),new IndirectMemoryReference(0,r14()));
                acfunc.virtualPop(r14());
                acfunc.jmp(beginLable);
                //else
                acfunc.label(elseLabel);

                acfunc.virtualPop(ax());

            }


            return;
        }
        else {
            acfunc.mov(ax(),r14());//head address resetved in r14
            acfunc.xor(r15(), r15());//r15 = i
            Label beginLable = new Label("Malloc@Begin"+(mallocnum++));
            Label thenLabel = new Label("Malloc@Then"+(mallocnum++));
            Label elseLabel = new Label("Malloc@Else"+(mallocnum++));
            //r15 from offset 0
            //cmp ax and size
            acfunc.label(beginLable);
            acfunc.mov(r15(),ax());
            acfunc.cmp(cx(),ax());
            acfunc.setl(al());
            acfunc.movzx(al(),ax());
            acfunc.test(ax(),ax());
            acfunc.jnz(thenLabel);
            acfunc.jmp(elseLabel);
                //if ax < size
            acfunc.label(thenLabel);
            acfunc.inc(r15());//++i
            acfunc.mov(r15(),ax());
            acfunc.mul(new ImmediateValue(8), ax());
            acfunc.virtualPush(r14());
            acfunc.add(ax(),r14());
            acfunc.push(r15());
            acfunc.push(r14());
            HeapAllocate(dimList.subList(1,dimList.size()),node);
            acfunc.pop(r14());
            acfunc.pop(r15());
            acfunc.mov(ax(),new IndirectMemoryReference(0,r14()));
            acfunc.virtualPop(r14());
            acfunc.jmp(beginLable);
            //else
            acfunc.label(elseLabel);
            acfunc.mov(r14(),ax());
        }


    }



    @Override
    public void visit(This node) {
        acfunc.mov(r13(),ax());
    }

    private int locateLocalVars(Scope scope){
        return locateLocalVars(scope, 0);
    }
    private int locateLocalVars(Scope scope, int parentStackLen){
        int len = parentStackLen;
        for (Node vari : scope.getEntities().values()) {
            VarDecNode var;
            if (vari instanceof VarDecInBlockNode) {
                var = ((VarDecInBlockNode) vari).getVardec();
            }else if (vari instanceof VarDecNode){
                var = (VarDecNode) vari;
            }else continue;
            if (var.getMemoryReference() != null){//parameters
                continue;
            }
            len = AsmUtils.align(len + STACK_WORD_SIZE, STACK_WORD_SIZE);
            var.setMemoryReference(new IndirectMemoryReference(-len, bp()));

        }
        int maxLen = len;
        for (Scope s : scope.getChildren()){
            int childLen = locateLocalVars(s, len);
            maxLen = Math.max(maxLen, childLen);
        }
        return maxLen;
    }
    private Register ax(){
        return new Register(Register.RegisterClass.AX);
    }
    private Register eax(){
        return new Register(Register.RegisterClass.AX, Register.AsmType.INT32);
    }private Register edx(){
        return new Register(Register.RegisterClass.DX, Register.AsmType.INT32);
    }
    private Register cx(){return new Register(Register.RegisterClass.CX);}
    private Register dx(){return new Register(Register.RegisterClass.DX);}
    private Register al(){return new Register(Register.RegisterClass.AL);}
    private Register bp(){return new Register(Register.RegisterClass.BP);}
    private Register sp(){
        return new Register(Register.RegisterClass.SP);
    }
    private Register cl(){
        return new Register(Register.RegisterClass.CL);
    }
    private Register di(){
        return new Register(Register.RegisterClass.DI);
    }
    private Register si(){
        return new Register(Register.RegisterClass.SI);
    }
    private Register r8(){return new Register(Register.RegisterClass.R8);}
    private Register r9(){return new Register(Register.RegisterClass.R9);}
    private Register r14(){return new Register(Register.RegisterClass.R14);}
    private Register r13(){return new Register(Register.RegisterClass.R13);}
    private Register r15(){return new Register(Register.RegisterClass.R15);}

    static final Register.RegisterClass[] CALLEE_SAVE_REGISTERS = {
            Register.RegisterClass.BX, Register.RegisterClass.BP,
            Register.RegisterClass.SI, Register.RegisterClass.DI, Register.RegisterClass.R12
    };
    class StackFrame{
        public List<Register> saveRegs;
        public int lvarSize;
        public int tempSize;

        public StackFrame(){}
        public int saveRegsSize(){
            return saveRegs.size() * STACK_WORD_SIZE;
        }
        public int lvarOffset(){return saveRegsSize();}
        public int tempOffset(){return saveRegsSize() + lvarSize;}
        public int frameSize(){return saveRegsSize() + lvarSize + tempSize;}

    }
    
    void call(Symbol func){
        if (rsp % 16 != 0){
            acfunc.align(0,true);
            rsp += 8;
            flag = true;
        }else{acfunc.align(0,false);}
        acfunc.call(func);
        if (flag){
            acfunc.align(1,true);
            rsp -= 8;
            flag = false;
        }else{acfunc.align(1,false);}
    }




    private AssemblyCode optimize(AssemblyCode body) {
        body.apply(PeepholeOptimizer.defaultSet());
        body.reduceLabels();
        return body;
    }
}
