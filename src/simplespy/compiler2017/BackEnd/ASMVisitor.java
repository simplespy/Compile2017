package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;

/**
 * Created by spy on 5/18/17.
 */
public interface ASMVisitor {
    void visit(AssemblyCode asm);

    void visit(Assembly asm);
    void visit(Comment asm);
    void visit(Directive asm);
    void visit(Instruction asm);
    void visit(Label asm);

}
