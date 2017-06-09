package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.BackEnd.ASMVisitor;
import simplespy.compiler2017.NodeFamily.UnaryOpNode;

/**
 * Created by spy on 17/6/7.
 */
public class Unary extends Instruction {
    public UnaryOpNode.UnaryOp unaryOp;
    public Unary(UnaryOpNode.UnaryOp op, Operand value) {
        super("Uni");
        this.unaryOp = op;
        operands = new Operand[] {value};
    }
    @Override
    public void setDefUse() {
        if (operands[0] instanceof Register) def.add((Register) operands[0]);
        else use = operands[0].getRegisters(use);
    }
    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

}
