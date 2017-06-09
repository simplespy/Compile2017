package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 17/6/7.
 */
public class Branch extends Instruction {
    public Label then, otherwise;

    public Branch(Operand condition, Label then, Label otherwise){
        super("Branch");
        operands = new Operand[]{condition};
        this.then = then;
        this.otherwise = otherwise;
    }
    @Override
    public void setDefUse() {
        use = operands[0].getRegisters(use);
    }   @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

}
