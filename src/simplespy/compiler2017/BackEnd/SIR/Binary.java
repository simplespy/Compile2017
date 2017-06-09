package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.BackEnd.ASMVisitor;
import simplespy.compiler2017.NodeFamily.BinaryOpNode;

/**
 * Created by spy on 17/6/7.
 */
public class Binary extends Instruction {
    public BinaryOpNode.BinaryOp binaryOp;
    public Binary(BinaryOpNode.BinaryOp op, Operand left, Operand right){
        super(op.toString());
        this.binaryOp = op;
        operands = new Operand[] {right, left};
    }

    @Override
    public void setDefUse() {
        use = operands[0].getRegisters(use);
        if (operands[1] instanceof Register) def.add((Register) operands[1]);
        use = operands[1].getRegisters(use);
    }
    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

}
