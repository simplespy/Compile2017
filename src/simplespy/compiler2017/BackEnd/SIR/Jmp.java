package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 17/6/7.
 */
public class Jmp extends Instruction {
    public Label label;
    public Jmp(Label label){
        super("Jmp "+ label.toString());
        this.label = label;
    }
    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

}
