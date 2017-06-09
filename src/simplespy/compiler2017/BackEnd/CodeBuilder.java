package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.BackEnd.SIR.*;
import simplespy.compiler2017.FrontEnd.GlobalScope;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.Utils.AsmUtils;
import simplespy.compiler2017.Utils.ListUtils;

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

    public void buildFunction(Function func){
        ac.label(func.name);
        rsp = 0;
        StackFrame frame = new StackFrame();
        frame.lvarSize = locateLocalVars(func);
        AssemblyCode body = (compileStmts(func));
        frame.tempSize = func.parameterSavedWord * STACK_WORD_SIZE;
        generateFunctionBody(ac, body, frame);
    }

    static final public int STACK_WORD_SIZE = 8;
    private int locateLocalVars(Function func){
        int len = 0;

        for (Operand  virReg : func.registerMap.keySet()){
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
        prologue(file, frame.saveRegs, frame.frameSize());
        boolean printornot = (frame.frameSize() % 16 == 0);
        file.addAll(body.getAssemblies(), printornot);
        epilogue(file, frame.saveRegs);
    }

    private void prologue(AssemblyCode file, List<Register> saveRegs, int frameSize){

        file.push(bp);
        file.mov(sp, bp);
        if (frameSize > 0) file.sub(new ImmediateValue(frameSize), sp);
    }

    private void epilogue(AssemblyCode file, List<Register> saveRegs){
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
       asm.accept(this);
       if(asm.comment != null){
           acfunc.comment(asm.comment);
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
                if (!(right instanceof ImmediateValue)) acfunc.mov(right, ax);
                acfunc.sal(al, left);break;
            case SHR:
                if (!(right instanceof ImmediateValue)) acfunc.mov(right, ax);
                acfunc.shr(al, left);break;
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
                if (arg == null){
                    throw new Error();
                }
                acfunc.mov(transfer(arg), PARAS_REG[i]);
                ++i;

            }

        }
        acfunc.call((Symbol) ins.operands[0]);
        if (ins.operands.length == 2) acfunc.mov(ax, transfer(ins.operands[1]));
        curfunc.parameterSavedWord = Math.max(curfunc.parameterSavedWord, offset + 2);
    }

    @Override
    public void visit(Jmp ins) {acfunc.jmp(ins.label);}

    @Override
    public void visit(Labelline ins) { acfunc.label(ins.label);}

    @Override
    public void visit(Move ins) {
        Operand src = transfer(ins.operands[0]);
        Operand dest = transfer(ins.operands[1]);
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


    private Register bp = new Register(Register.RegisterClass.BP);
    private Register ax = new Register(Register.RegisterClass.AX);
    private Register dx = new Register(Register.RegisterClass.DX);
    private Register cx = new Register(Register.RegisterClass.CX);
    private Register cl = new Register(Register.RegisterClass.CL);
    private Register al = new Register(Register.RegisterClass.AL);
    private Register sp = new Register(Register.RegisterClass.SP);
    private Register di = new Register(Register.RegisterClass.DI);
    private Register si = new Register(Register.RegisterClass.SI);
    private Register r8 = new Register(Register.RegisterClass.R8);
    private Register r9 = new Register(Register.RegisterClass.R9);
    private Register[] PARAS_REG = {di, si, dx, cx, r8, r9};

}
