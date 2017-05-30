package simplespy.compiler2017.BackEnd;

import org.stringtemplate.v4.ST;
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
        file.define(new ImmediateValue(0),new Symbol("\"%d\""));
        file.label("fmts");
        file.define(new ImmediateValue(0),new Symbol("\"%s\""));
        gvars.stream().filter(x->x.init != null && !(x.init instanceof NullLiteralNode)).forEachOrdered(x ->{
            Symbol sym = file.label("var@"+x.getName());
            x.setAddress(sym );
            x.setMemoryReference(new DirectMemoryReference(sym));
            initialize(x);
        });
        file._bss();
        gvars.stream().filter(x->x.init == null).forEachOrdered(x ->{
            file.addBss("var@"+x.getName()+"\tresq \t1");
        });
        gvars.stream().forEachOrdered(this::visit);
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
        }else if (node.getLeft() instanceof Int && node.getRight() instanceof Expr){
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
    }

    private void compileFunctionBody(AssemblyCode file, FuncDefNode func){
        StackFrame frame = new StackFrame();
        locateParameters(func.parameters);
        frame.lvarSize = locateLocalVars(func.scope);
        AssemblyCode body = compileStmts(func);
        frame.saveRegs = usedCalleeSaveRegisters(body);
        frame.tempSize = ac.virtualStack.getMax();

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
                ((VarDecInBlockNode) var).getVardec().getMemoryReference().fixOffset(-len);
            }
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

    private void compileExpr(Expr node, Register reg){
        if(node instanceof Var){
            acfunc.mov(node.getMemoryReference(), reg);
        }
        else if (node instanceof  Int){
            acfunc.mov(new ImmediateValue(((Int)node).getValue()), reg);
        }
        else if (node instanceof Str){
            acfunc.mov(stringPool.get(((Str)node).getValue()).getMemoryReference(), reg);
        }
        else{
            throw new Error("compile expr");
        }
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

            acfunc.push(r15());//length of left
            acfunc.push(r14());
            acfunc.push(r13());
            rsp += 8;

            visit(node.getLeft());
            acfunc.mov(ax(),r14());
            acfunc.mov(ax(),di());
            call(strlen);
            acfunc.mov(ax(),r15());

            visit(node.getRight());
            acfunc.mov(ax(),r13());
            acfunc.mov(ax(),di());
            call(strlen);
            acfunc.add(r15(),ax());//new length

            Symbol buffer = new Symbol(varBase + varnum++);
            ac.addBss(buffer.name+':' + "\tresq  20" );

            Symbol malloc = new Symbol("malloc");
            acfunc.addExtern(malloc);
            acfunc.mov(ax(),di());
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
            acfunc.pop(r13());//length of left
            acfunc.pop(r14());
            acfunc.pop(r15());
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
            ac.addBss(var.name+':' + "\tresq  20" );
            visit(node.getArgs().get(0));
            acfunc.mov(ax(), dx());
            acfunc.mov(new Symbol("fmtd"), si());
            acfunc.mov(var, di());

            call(new Symbol(transFuncName(funcName)));

            acfunc.mov(var,ax());
            /*  acfunc.push(r15());
            acfunc.mov(new ImmediateValue(20), di());
              call(new Symbol("malloc"));
            acfunc.mov(ax(),r15());
            acfunc.add(new ImmediateValue(8), r15());
            acfunc.mov(r15(), di());
            acfunc.mov(new Symbol("fmtd"), si());
            acfunc.pop(dx());
              call(new Symbol(transFuncName(funcName)));
            acfunc.mov(r15(), di());
              call(new Symbol("strlen"));
            acfunc.mov(ax(),new Symbol("qword [r15 - 8]"));
            acfunc.mov(r15(),ax());
            acfunc.pop(r15());
            acfunc.ret();*/
        }else if (funcName.equals("length") && entity.equals(gl.string.get(funcName))){
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
                acfunc.mov(ax(),cx());//first char
                visit(node.getArgs().get(0));
                acfunc.mov(ax(),di());//arg
                acfunc.add(di(),cx());
                acfunc.mov(new IndirectMemoryReference(0,cx()), ax());
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
            acfunc.push(r15());
            acfunc.push(r14());
            visit(node.argThis);
            acfunc.mov(ax(), di());//address
            visit(node.getArgs().get(0));
            acfunc.mov(ax(),si());//int left
            visit(node.getArgs().get(1));
            acfunc.mov(ax(),dx());//int right

            acfunc.mov(di(),r15());
            acfunc.add(si(),r15());//address
            acfunc.mov(dx(),r14());
            acfunc.add(new ImmediateValue(1), r14());
            acfunc.sub(si(),r14());//length

            acfunc.mov(r14(),di());
            call(malloc);

            acfunc.mov(ax(),di());//new Address
            acfunc.mov(r15(),si());
            acfunc.mov(r14(),dx());
            call(memcpy);

            acfunc.mov(ax(),r15());
            acfunc.add(r14(),r15());
            acfunc.mov(new ImmediateValue(0), r15());
            acfunc.pop(r14());
            acfunc.pop(r15());



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
               // acfunc.sub(new ImmediateValue(8), sp());
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
            acfunc.mov(new DirectMemoryReference(node.getAddress()), eax());
        }
        else if(entity.getType() instanceof ArrayType || entity.getType() instanceof ClassType){
            acfunc.mov(node.getMemoryReference(),eax());
        }
        else{
            acfunc.lea(node.getMemoryReference(),eax());
        }
    }

    @Override
    public void visit(Mem node) {
        visit(node.expr);
        acfunc.mov(new IndirectMemoryReference(0, ax()), eax());
    }

    @Override
    public void visit(Malloc node) {
        if (acfunc == null) acfunc = ac;
        Symbol malloc = new Symbol("malloc");
        acfunc.addExtern(malloc);
        visit(node.spaceSize);
        acfunc.mov(ax(),di());

        call(malloc);

        if (node.arraySize != null) {
            acfunc.virtualPush(ax());
            visit(node.arraySize);
            acfunc.virtualPop(cx());
            acfunc.mov(ax(), new IndirectMemoryReference(0,cx()));
            acfunc.mov(cx(), ax());
        }
      //  acfunc.add(new ImmediateValue(STACK_WORD_SIZE), sp());
       /* acfunc.mov(new ImmediateValue(12), ax());
        acfunc.mov(new ImmediateValue(0), di());
        acfunc.syscall();
        DirectMemoryReference initial = new DirectMemoryReference(new Symbol("initial_break"));
        DirectMemoryReference current = new DirectMemoryReference(new Symbol("current_break"));
        acfunc.mov(ax(), initial);
        acfunc.mov(ax(), current);
        visit(node.spaceSize);
        acfunc.mov(current, di());
        acfunc.add(ax(),di());
        acfunc.mov(new ImmediateValue(12), ax());
        acfunc.syscall();
        acfunc.mov(ax(),current);*/

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
            }else break;
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
        return new Register(Register.RegisterClass.AX, AsmType.INT32);
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
            Register.RegisterClass.SI, Register.RegisterClass.DI
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

}
