package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.BackEnd.ASMVisitor;

import java.util.List;

/**
 * Created by spy on 17/6/7.
 */
public class CallFunc extends Instruction {
    public List<Operand> paras;
    public CallFunc(Operand func, List<Operand> paras) {
        super("Call");
        this.paras = paras;
        operands = new Operand[]{func};
    }
    public CallFunc(Operand func, List<Operand> paras, Operand result) {
        super("Call");
        this.paras = paras;
        operands = new Operand[]{func, result};
    }
    @Override
    public void setDefUse() {
        use = operands[0].getRegisters(use);
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }

}
