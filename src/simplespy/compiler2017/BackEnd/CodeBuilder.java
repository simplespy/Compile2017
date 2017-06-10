package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.BackEnd.SIR.*;
import simplespy.compiler2017.FrontEnd.GlobalScope;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.Utils.AsmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/6/8.
 */
public class CodeBuilder implements ASMVisitor{
    public SIR ir;
    public AssemblyCode ac;
    public AssemblyCode acfunc;
    private Map<String, StringLiteralNode> stringPool;
    GlobalScope gl;
    private Function curfunc;
    public AssemblyCode getAC() {
        return ac;
    }
    public int rsp;


    public CodeBuilder(SIR ir){
        this.ir = ir;
    }

    public void build(){
        stringPool = ir.typeTable.getStringMap();
        gl = ir.scope;
        ac = new AssemblyCode();
        generateDataSection(ac, ir.vars);
        generateTextSection(ac);

    }

    private void generateDataSection(AssemblyCode file, List<VarDecNode> gvars){
        file._data();
        gvars.stream().forEachOrdered(x->{file.label(x.getAddress().name);file.define(new ImmediateValue(0));});

        stringPool.keySet().stream().forEachOrdered(name -> {
            Symbol symbol = new Symbol('`'+name+'`');
            file.define(new ImmediateValue(name.length()));
            file.label(stringPool.get(name).getAddress().name);
            for (int i = 0; i < name.length(); ++i){
                if (name.charAt(i) == '\\'){
                    ++i;
                    switch (name.charAt(i)){
                        case 'n' : file.define("db", new ImmediateValue(10)); break;
                        case '\\' : file.define("db", new ImmediateValue(92)); break;
                        case 't' : file.define("db", new ImmediateValue(9)); break;
                        case 'r' : file.define("db", new ImmediateValue(13)); break;
                        case '\"' : file.define("db", new ImmediateValue(34)); break;
                    }
                }
                else file.define("db", new ImmediateValue(name.charAt(i)));
            }
            file.define("db", new ImmediateValue(0));
        });
    }

    private void generateTextSection(AssemblyCode file){
        file._text();
        ir.functionList.forEach(this::buildFunction);

    }
    int flag = 1;
    public void buildFunction(Function func){
        ac.label(func.name);
        if (func.name.equals("main")) flag = 0;
        else flag = 1;
        rsp = 0;
        StackFrame frame = new StackFrame();
        frame.lvarSize = locateLocalVars(func);
        AssemblyCode body = (compileStmts(func));
        generateFunctionBody(ac, body, frame);
    }

    static final public int STACK_WORD_SIZE = 8;
    private int locateLocalVars(Function func){
        int len = 0;

        for (Register  virReg : func.registerMap.keySet()){
            if (func.registerMap.get(virReg) == null){
                len = AsmUtils.align(len + STACK_WORD_SIZE, STACK_WORD_SIZE);
                func.registerMap.replace(virReg, new IndirectMemoryReference(-len, bp));
            }
        }
        curfunc = func;
        return len;
    }
    private AssemblyCode compileStmts(Function func){
        acfunc = new AssemblyCode();
        func.instructions.stream().forEachOrdered(this::visit);
        return acfunc;
    }

    private void generateFunctionBody(AssemblyCode file, AssemblyCode body, StackFrame frame){
        prologue(file, frame.frameSize());
        boolean printornot = (frame.frameSize() % 16 == 0);
        file.addAll(body.getAssemblies(), printornot);
        epilogue(file, frame.frameSize());
    }

    private void prologue(AssemblyCode file, int frameSize){
        file.push(bp);
        file.mov(sp, bp);
        if (flag == 1) save_Callee(file);
        if (frameSize > 0) file.sub(new ImmediateValue(frameSize), sp);
    }

    private void epilogue(AssemblyCode file, int frameSize){
        if (frameSize > 0) file.add(new ImmediateValue(frameSize), sp);
        if (flag == 1) pop_Callee(file);
        file.mov(bp,sp);
        file.pop(bp);
        file.ret();
    }

    @Override
    public void visit(AssemblyCode asm) {}

    @Override
    public void visit(Assembly asm) {}

    @Override
    public void visit(Directive asm) {}

    @Override
    public void visit(Instruction asm) {
        if (asm.op.equals("save_caller")) save_Caller();
        else {
            asm.accept(this);
            if(asm.comment != null){
                acfunc.comment(asm.comment);
            }
        }
    }

    @Override
    public void visit(Label asm) {}

    @Override
    public void visit(Align asm) {}

    @Override
    public void visit(Binary ins) {
        Operand left = transfer(ins.operands[1]);
        Operand right = transfer(ins.operands[0]);

        switch (ins.binaryOp) {
            case ADD:
                if(left.isMem() && right.isMem()){
                    acfunc.mov(right, ax);
                    right = ax;
                }
                acfunc.add(right, left);
                break;
            case SUB:
                if(left.isMem() && right.isMem()){
                    acfunc.mov(right, ax);
                    right = ax;
                }
                acfunc.sub(right, left);
                break;
            case MUL:
                acfunc.mov(left, ax);
                acfunc.mul(right, ax);
                acfunc.mov(ax, left);
                break;
            case DIV:case MOD:
                acfunc.mov(new ImmediateValue(0),dx);
                acfunc.mov(left, ax);
                acfunc.div(right);
                if (ins.binaryOp.equals(BinaryOpNode.BinaryOp.MOD)) {
                    acfunc.mov(dx, left);
                }
                else acfunc.mov(ax, left);
                break;
            case BITWISE_AND:
                if(left.isMem() && right.isMem()){
                    acfunc.mov(right, ax);
                    right = ax;
                }
                acfunc.and(right, left);break;
            case BITWISE_OR:
                if(left.isMem() && right.isMem()){
                    acfunc.mov(right, ax);
                    right = ax;
                }
                acfunc.or(right, left);break;
            case XOR:
                if(left.isMem() && right.isMem()){
                    acfunc.mov(right, ax);
                    right = ax;
                }
                acfunc.xor(right, left);break;
            case SHL:
                acfunc.mov(right, cx);
                acfunc.mov(left, ax);
                acfunc.sal(cl, ax);
                acfunc.mov(ax, left);
                break;
            case SHR:
                acfunc.mov(right, cx);
                acfunc.mov(left, ax);
                acfunc.shr(cl, ax);
                acfunc.mov(ax, left);
                break;
            default:// Comparison operators
                acfunc.mov(left, ax);
                acfunc.cmp(right, ax);
                switch (ins.binaryOp) {
                    case EQ:    acfunc.sete (al); break;
                    case NE:    acfunc.setne(al); break;
                    case GT:    acfunc.setg (al); break;
                    case GE:    acfunc.setge(al); break;
                    case LT:    acfunc.setl (al); break;
                    case LE:    acfunc.setle(al); break;
                }
                acfunc.movzx(al, ax);
                acfunc.mov(ax, left);
        }
    }

    @Override
    public void visit(Branch ins) {
        Operand condition = transfer(ins.operands[0]);
        acfunc.mov(condition,ax);
        acfunc.test(ax,ax);
        acfunc.jnz(ins.then);
        acfunc.jmp(ins.otherwise);
    }

    @Override
    public void visit(CallFunc ins) {
        int parasavedSize;
        if (ins.paras.size() <= 6) parasavedSize = 0;
        else if ((ins.paras.size() - 6) % 2 == 1) parasavedSize = ins.paras.size() - 5;
        else parasavedSize = ins.paras.size() - 6;

        if (parasavedSize > 0) acfunc.sub(new ImmediateValue(parasavedSize * STACK_WORD_SIZE), sp);

        int i = 0;
        int offset = 0;
        for (Operand arg: ins.paras){
            if(i >= PARAS_REG.length) {
                Operand realArg = transfer(arg);
                if (realArg.isMem()){
                    acfunc.mov(realArg, ax);
                    acfunc.mov(ax, new IndirectMemoryReference(offset*STACK_WORD_SIZE, sp));
                }
                else acfunc.mov(realArg, new IndirectMemoryReference(offset*STACK_WORD_SIZE, sp));
                ++offset;
            }
            else {
                //acfunc.mov(transfer(arg), PARAS_REG[i]);
                ++i;

            }

        }
        acfunc.call((Symbol) ins.operands[0]);
        // if (ins.operands.length == 2) acfunc.mov(ax, transfer(ins.operands[1]));
        curfunc.parameterSavedWord = Math.max(curfunc.parameterSavedWord, offset + 2);
        if (parasavedSize > 0) acfunc.add(new ImmediateValue(parasavedSize * STACK_WORD_SIZE), sp);

        pop_Caller();
    }

    @Override
    public void visit(Jmp ins) {acfunc.jmp(ins.label);}

    @Override
    public void visit(Labelline ins) { acfunc.label(ins.label);}

    @Override
    public void visit(Move ins) {
        Operand src = transfer(ins.operands[0]);
        Operand dest = transfer(ins.operands[1]);
        if (src.equals(dest)) return;
        if (src.isMem() && dest.isMem()){
            acfunc.mov(src, ax);
            acfunc.mov(ax, dest);
        }
        else acfunc.mov(src, dest);
    }

    @Override
    public void visit(Unary ins) {
        Operand value = transfer(ins.operands[0]);
        switch (ins.unaryOp){
            case NEG:
                acfunc.neg(value);
                break;
            case LOGICAL_NOT:
                acfunc.mov(value, ax);
                acfunc.test(ax,ax);
                acfunc.sete(al);
                acfunc.movzx(al,cx);
                acfunc.mov(cx, value);
                break;
            case BITWISE_NOT:
                acfunc.not(value);
                break;
            default:
        }
    }

    class StackFrame{
        public List<Register> saveRegs = new ArrayList<>();
        public int lvarSize;
        public int tempSize;

        public StackFrame(){rsp = 0;}
        public int saveRegsSize(){
            return saveRegs.size() * STACK_WORD_SIZE;
        }
        public int lvarOffset(){return saveRegsSize();}
        public int tempOffset(){return saveRegsSize() + lvarSize;}
        public int frameSize(){return saveRegsSize() + lvarSize + tempSize;}

    }

    Operand transfer(Operand key){
        if (curfunc.registerMap.containsKey(key)) return curfunc.registerMap.get(key);
        while (key instanceof MemoryReference && curfunc.registerMap.containsKey(((MemoryReference)key).addr)){
            acfunc.mov(curfunc.registerMap.get(((MemoryReference)key).addr), cx);
            return new MemoryReference(cx);
        }
        return key;

    }
    private void save_Caller(){
        for (Register reg : CALLER_SAVED_REG){
            acfunc.push(reg);
        }
    }
    private void pop_Caller(){
        for (int i = CALLER_SAVED_REG.length-1; i >= 0; --i){
            acfunc.pop(CALLER_SAVED_REG[i]);
        }
    }
    private void save_Callee(AssemblyCode file){
        for (Register reg : CALLEE_SAVED_REG){
            file.push(reg);
        }
        file.sub(new ImmediateValue(8), sp);
    }
    private void pop_Callee(AssemblyCode file){
        file.add(new ImmediateValue(8), sp);

        for (int i = CALLEE_SAVED_REG.length-1; i >= 0; --i){
            file.pop(CALLEE_SAVED_REG[i]);
        }
    }



    private Register ax = Register.ax;
    private Register bx = Register.bx;
    private Register cx = Register.cx;
    private Register dx = Register.dx;
    private Register di = Register.di;
    private Register si = Register.si;
    private Register r8 = Register.r8;
    private Register r9 = Register.r9;
    private Register r10 = Register.r10;
    private Register r11 = Register.r11;
    private Register r12 = Register.r12;
    private Register r13 = Register.r13;
    private Register r14 = Register.r14;
    private Register r15 = Register.r15;

    private Register bp = Register.bp;
    private Register sp = Register.sp;
    private Register al = new Register(Register.RegisterClass.AL);
    private Register cl = new Register(Register.RegisterClass.CL);

    private Register[] PARAS_REG = {di, si, dx, cx, r8, r9};
    private Register[] CALLER_SAVED_REG = {di, si, r8, r9, r10, r11};
    private Register[] CALLEE_SAVED_REG = {bx, r12, r13, r14, r15};
}
