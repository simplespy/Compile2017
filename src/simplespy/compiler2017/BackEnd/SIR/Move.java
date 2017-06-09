package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 17/6/7.
 */
public class Move extends Instruction {


    public Move(Operand src, Operand dest) {
        super("Mov");
        this.operands = new Operand[] {src, dest};
    }

    @Override
    public void setDefUse() {
        use = operands[0].getRegisters(use);
        if (operands[1] instanceof Register) def.add((Register) operands[1]);
        else use = operands[1].getRegisters(use);
    }
    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

}
