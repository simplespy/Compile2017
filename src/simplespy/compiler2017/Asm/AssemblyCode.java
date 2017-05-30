package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.ASMVisitor;

import java.util.*;

/**
 * Created by spy on 5/18/17.
 */
public class AssemblyCode {
    List<Assembly> assemblies = new ArrayList<>();
    List<Assembly> externs = new ArrayList<>();
    List<String> externList = new LinkedList<>();
    List<String> bss = new ArrayList<>();
    Map<String, String> builtinFunctions = new LinkedHashMap<>();
    List<String> internList = new ArrayList<>();

    public static SymbolTable table = new SymbolTable("L");

    public Statistics statistics;
    
    public VirtualStack virtualStack = new VirtualStack();
    final int stackWordSize = 8;

    public AssemblyCode(){
        setBuiltinFunctions();
    }


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

    public void addIntern(String name){
        if (internList.contains(name)) return;
        internList.add(name);
    }
    public void addIntern(List<String> interns){
        interns.stream().filter(x->!this.internList.contains(x)).forEachOrdered(x->this.internList.add(x));
    }

    public List<String> printIntern(){
        List<String> print = new ArrayList<>();
        this.internList.stream().forEachOrdered(x->print.add(builtinFunctions.get(x)));
        return print;

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

    public void cltd(){assemblies.add(new Instruction("cltd"));}

    public void movzx(Operand src, Operand dest){
        assemblies.add(new Instruction("movzx", src, dest));
    }
    public void add(Operand src, Register dest) {
        assemblies.add(new Instruction("add", src, dest));
    }
    public void sub(Operand src, Register dest) {
        assemblies.add(new Instruction("sub", src, dest));
    }
    public void mul(Operand src, Register dest) {
        assemblies.add(new Instruction("imul", src, dest));
    }
    public void and(Operand src, Register dest) {
        assemblies.add(new Instruction("and", src, dest));
    }
    public void or(Operand src, Register dest) {
        assemblies.add(new Instruction("or", src, dest));
    }
    public void xor(Operand src, Register dest) {
        assemblies.add(new Instruction("xor", src, dest));
    }
    public void sal(Operand src, Register dest) {
        assemblies.add(new Instruction("sal", src, dest));
    }
    public void shr(Operand src, Register dest) {
        assemblies.add(new Instruction("shr", src, dest));
    }
    public void inc(Operand src) {
        assemblies.add(new Instruction("inc", src));
    }


    public void div(Operand src) {
        assemblies.add(new Instruction("div", src));
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
    public void virtualPush(Register reg) {
        virtualStack.extend(stackWordSize);
        mov(reg, virtualStack.top());
    }

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
    public void define(Operand value, Operand suffix){
        assemblies.add(new Instruction("dq", value, suffix));
    }
    public void virtualPop(Register reg){
        mov(virtualStack.top(), reg);
        virtualStack.rewind(stackWordSize);
    }
    public boolean doesUses(Register reg) {
        return statistics().doesRegisterUsed(reg);
    }



    private void setBuiltinFunctions(){
        builtinFunctions.put("parseInt",
                "@string.parseInt:\n" +
                "    mov rdx, qword [rdi]\n" +
                "    xor rax, rax\n" +
                "    xor rcx, rcx\n" +
                "__loop@string.parseInt:\n" +
                "    cmp rdx, 0\n" +
                "    je __loop_exit@string.parseInt\n" +
                "    mov cl, byte [rdi+8]\n" +
                "    cmp cl, 48\n" +
                "    jl __loop_exit@string.parseInt\n" +
                "    cmp cl, 57\n" +
                "    jg __loop_exit@string.parseInt\n" +
                "    \n" +
                "    imul rax, 10\n" +
                "    sub rcx, 48\n" +
                "    add rax, rcx\n" +
                "    inc rdi\n" +
                "    dec rdx\n" +
                "    jmp __loop@string.parseInt\n" +
                "__loop_exit@string.parseInt:\n" +
                "    ret");
        builtinFunctions.put("substring",
                "@string.substring:\n" +
                "    add rdi, rsi\n" +
                "    push rdi\n" +
                "    push rsi\n" +
                "    push rdx\n" +
                "    mov rdi, rdx\n" +
                "    sub rdi, rsi\n" +
                "    add rdi, 9\n" +
                "    call malloc\n" +
                "    pop rdx\n" +
                "    pop rsi\n" +
                "    pop rdi\n" +
                "    sub rdx, rsi\n" +
                "    inc rdx\n" +
                "    mov qword [rax], rdx\n" +
                "    xor rsi, rsi\n" +
                "    add rdi, 8\n" +
                "__loop@string.substring:\n" +
                "    cmp rdx, 0\n" +
                "    je __loop_exit@string.substring\n" +
                "    mov cl, byte [rdi]\n" +
                "    mov byte [rax+rsi+8], cl\n" +
                "    inc rsi\n" +
                "    inc rdi\n" +
                "    dec rdx\n" +
                "    jmp __loop@string.substring\n" +
                "__loop_exit@string.substring:\n" +
                "    ret");
    }









    public class VirtualStack{
        private int offset;
        private int max;
        private List<IndirectMemoryReference> memrefs = new ArrayList<>();;
        public VirtualStack(){
            reset();
        }
        public void reset(){
            offset = 0;
            max = 0;
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
            IndirectMemoryReference mem = new IndirectMemoryReference(-offset, new Register(Register.RegisterClass.BP));
            memrefs.add(mem);
            return mem;
        }

        public void fixOffset(int diff) {
            for (MemoryReference mem : memrefs) {
                mem.fixOffset(diff);
            }
        }



    }
    

}
