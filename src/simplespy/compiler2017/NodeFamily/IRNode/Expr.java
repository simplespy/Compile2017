package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.ImmediateValue;
import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/15.
 */
abstract public class Expr   {
    public Operand result;

    public void setResult(Operand result) {
        this.result = result;
    }

    public Operand getResult(){return result;}

    abstract public void accept(IRVisitor visitor);
    public Node getEntityForce(){
        return null;
    }
    public boolean isConstant(){
        return false;
    }
    public boolean isAddr(){
        return false;
    }
    public boolean isVar(){return false;}
    public Expr addressNode() {
        throw new Error("unexpected node for LHS: " + getClass());
    }
    public ImmediateValue asmValue() {
        throw new Error("Expr#asmValue called");
    }
    public MemoryReference getMemoryReference() {
        throw new Error("Expr#memref called");
    }
    public boolean isString(){return false;}
}
