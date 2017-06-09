package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.BackEnd.SIR.*;

/**
 * Created by spy on 5/18/17.
 */
public interface ASMVisitor {
    void visit(AssemblyCode asm);

    void visit(Assembly asm);
    void visit(Directive asm);
    void visit(Instruction asm);
    void visit(Label asm);
    void visit(Align asm);

    void visit(Binary ins);
    void visit(Branch ins);
    void visit(CallFunc ins);
    void visit(Jmp ins);
    void visit(Labelline ins);
    void visit(Move ins);
    void visit(Unary ins);


}
