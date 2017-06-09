package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.ASMVisitor;
import simplespy.compiler2017.BackEnd.PeepholeOptimizer;

import java.util.*;

/**
 * Created by spy on 5/18/17.
 */
public class AssemblyCode {
    List<Assembly> assemblies = new ArrayList<>();
    List<Assembly> externs = new ArrayList<>();
    List<String> externList = new LinkedList<>();
    List<String> bss = new ArrayList<>();

    public static SymbolTable table = new SymbolTable("L");

    public Statistics statistics;
    
    public VirtualStack virtualStack = new VirtualStack();
    final int stackWordSize = 8;



    private Statistics statistics() {
        if (statistics == null) {
            statistics = Statistics.collect(assemblies);
        }
        return statistics;
    }
    public void addAll(List<Assembly> assemblies, boolean is){
        assemblies.stream().forEachOrdered(x->{
            if(x instanceof Align){
                if (((Align) x).isadded == is){
                    this.assemblies.add(x);
                }
            }else {
                this.assemblies.add(x);
            }
        });
    }



    public void addExtern(Operand name){
        String newString = name.toString();
        if (externList.contains(newString)) return;
        externs.add(new Instruction("extern", name));
        externList.add(newString);
    }
    public void addExtern(List<Assembly> externs){
        externs.stream().filter(x->!this.externList.contains(x)).forEachOrdered(x->this.externs.add(x));
    }

    public List<Assembly> getExterns() {
        return externs;
    }

    public void _bss(){bss.add("\tsection  .bss");}

    public void addBss(String string){bss.add(string);}

    public List<String> getBss() {
        return bss;
    }

    public void mov(Operand src, Operand dest) {
        assemblies.add(new Instruction("mov", src, dest));
    }

    public void push(Register reg){
        assemblies.add(new Instruction("push", reg));
    }

    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

    public List<Assembly> getAssemblies() {
        return assemblies;
    }

    public void align(int kind, boolean is){
        assemblies.add(new Align(kind, is));
    }

    public void _data(){
        assemblies.add(new Directive("\tsection  .data"));
    }
    public void _text(){
        assemblies.add(new Directive("\tsection  .text"));
    }
    public void _rodata(){
        assemblies.add(new Directive("\tsection  .rodata"));
    }

    public void syscall(){assemblies.add(new Instruction("syscall"));}

    public void jmp(Label label) {
        assemblies.add(new Instruction("jmp", label.getSymbol()));
    }
    public void jnz(Label label){
        assemblies.add(new Instruction("jnz", label.getSymbol()));
    }
    public void jz(Label label){
        assemblies.add(new Instruction("jz", label.getSymbol()));
    }
    public void jle(Label label){
        assemblies.add(new Instruction("jle", label.getSymbol()));
    }
    public void jge(Label label){assemblies.add(new Instruction("jge", label.getSymbol()));}
    public void jg(Label label){assemblies.add(new Instruction("jg", label.getSymbol()));}
    public void jne(Label label){assemblies.add(new Instruction("jne", label.getSymbol()));}
    public void jl(Label label){assemblies.add(new Instruction("jl", label.getSymbol()));}

    public void test(Operand a, Operand b){
        assemblies.add(new Instruction("test", a, b));
    }
    public void neg(Operand src){assemblies.add(new Instruction("neg", src));}
    public void not(Operand src){assemblies.add(new Instruction("not", src));}

    public void sete(Operand src){assemblies.add(new Instruction("sete",src));}
    public void setne(Operand src){assemblies.add(new Instruction("setne",src));}
    public void setl(Operand src){assemblies.add(new Instruction("setl",src));}
    public void setle(Operand src){assemblies.add(new Instruction("setle",src));}
    public void setg(Operand src){assemblies.add(new Instruction("setg",src));}
    public void setge(Operand src){assemblies.add(new Instruction("setge",src));}

    public void movsx(Operand src, Operand dest){
        assemblies.add(new Instruction("movsx", src, dest));
    }

    public void movzx(Operand src, Operand dest){
        assemblies.add(new Instruction("movzx", src, dest));
    }
    public void add(Operand src, Operand dest) {
        assemblies.add(new Instruction("add", src, dest));
    }
    public void sub(Operand src, Operand dest) {
        assemblies.add(new Instruction("sub", src, dest));
    }
    public void mul(Operand src, Operand dest) {
        assemblies.add(new Instruction("imul", src, dest));
    }
    public void and(Operand src, Operand dest) {
        assemblies.add(new Instruction("and", src, dest));
    }
    public void or(Operand src, Operand dest) {
        assemblies.add(new Instruction("or", src, dest));
    }
    public void xor(Operand src, Operand dest) {
        assemblies.add(new Instruction("xor", src, dest));
    }
    public void sal(Operand src, Operand dest) {
        assemblies.add(new Instruction("sal", src, dest));
    }
    public void shr(Operand src, Operand dest) {
        assemblies.add(new Instruction("shr", src, dest));
    }
    public void inc(Operand src) {
        assemblies.add(new Instruction("inc", src));
    }
    public void dec(Operand src) {
        assemblies.add(new Instruction("dec", src));
    }

    public void div(Operand src) {
        assemblies.add(new Instruction("idiv", src));
    }
    public void mod(Operand src, Register dest) {
        assemblies.add(new Instruction("mod", src, dest));
    }

    public void cmp(Operand src, Register dest) {
        assemblies.add(new Instruction("cmp", src, dest));
    }
    public void call(Symbol symbol){
        assemblies.add(new Instruction("call", symbol));
    }
    public void pop(Register reg){assemblies.add(new Instruction("pop",reg));}
    public void ret(){
        assemblies.add(new Instruction("ret"));
    }

    public void lea(Operand src, Register dest) {
        assemblies.add(new Instruction("lea", src, dest));
    }
    public void comment(String comment){ assemblies.get(assemblies.size()-1).setComment(comment);}

    public Symbol label(String labelName){
        Symbol symbol = new Symbol(labelName);
        assemblies.add(new Label((labelName)));
        return symbol;
    }
    public void label(Label label){
        assemblies.add(label);
    }
    public void define(Operand value){
        assemblies.add(new Instruction("dq", value));
    }
    public void define(String length, Operand value){
        assemblies.add(new Instruction(length, value));
    }
    public void define(Operand value, Operand suffix){
        assemblies.add(new Instruction("dq", value, suffix));
    }

    public boolean doesUses(Register reg) {
        return statistics().doesRegisterUsed(reg);
    }

    public void apply(PeepholeOptimizer opt) {
        assemblies = opt.optimize(assemblies);
    }
    public void reduceLabels() {
        Statistics stats = statistics();
        List<Assembly> result = new ArrayList<Assembly>();
        for (Assembly asm : assemblies) {
            if (asm instanceof Label && ! stats.doesSymbolUsed((Label)asm)) {
                continue;
            }
            else {
                result.add(asm);
            }
        }
        assemblies = result;
    }
    public void virtualPush(Register reg) {
        if (virtualStack.cachePointer < 0){
            mov(reg, new Register(virtualStack.CACHE_REGISTERS[virtualStack.cachePointer]));
            ++virtualStack.cachePointer;
        }else {
            virtualStack.extend(stackWordSize);
            mov(reg, virtualStack.top());
        }
    }

    public void virtualPop(Register reg){
        if (virtualStack.cachePointer > 0){
            --virtualStack.cachePointer;
            mov(new Register(virtualStack.CACHE_REGISTERS[virtualStack.cachePointer]),reg);
        }else {
            mov(virtualStack.top(), reg);
            virtualStack.rewind(stackWordSize);
        }
    }
    public class VirtualStack{
        public final Register.RegisterClass[] CACHE_REGISTERS =
                {Register.RegisterClass.R12};
        public int cachePointer;
        private int offset;
        private int max;
        private List<IndirectMemoryReference> memrefs = new ArrayList<>();;
        public VirtualStack(){
            reset();
        }
        public void reset(){
            offset = 0;
            max = 0;
            cachePointer = 0;
            memrefs.clear();
        }
        void extend(int len) {
            offset += len;
            max = Math.max(offset, max);

        }

        public int getMax() {
            return max;
        }

        void rewind(int len) {
            offset -= len;
        }

        public IndirectMemoryReference top(){
            IndirectMemoryReference mem = relocatableMem(-offset, new Register(Register.RegisterClass.BP));
            memrefs.add(mem);
            return mem;
        }

        private IndirectMemoryReference relocatableMem(int offset, Register base) {
            return IndirectMemoryReference.relocatable(offset, base);
        }

        public void fixOffset(int diff) {
            for (MemoryReference mem : memrefs) {
                mem.fixOffset(diff);
            }
        }



    }
    

}
