package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.ImmediateValue;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.FrontEnd.IRVisitor;

/**
 * Created by spy on 17/4/15.
 */
public class Int extends Expr{
    protected int value;
    Register.AsmType type;

    public Int(int value, Register.AsmType type) {
        this.value = value;
        this.type = type;
    }
    public Int(int value) {
       this(value, Register.AsmType.INT64);
    }

    public int getValue() {
        return value;
    }

    public boolean isConstant() { return true; }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public ImmediateValue asmValue() {
        return new ImmediateValue(value);
    }


}
