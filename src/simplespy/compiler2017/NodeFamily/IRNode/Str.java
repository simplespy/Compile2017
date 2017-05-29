package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.ImmediateValue;
import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.StringLiteralNode;

/**
 * Created by spy on 17/4/15.
 */
public class Str extends Expr {
    String value;
    StringLiteralNode entry;

    public Str(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public boolean isConstant() { return true; }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public ImmediateValue asmValue() {
        return entry.address;
    }

    public MemoryReference getMemoryReference() {
        return entry.getMemoryReference();
    }
}
