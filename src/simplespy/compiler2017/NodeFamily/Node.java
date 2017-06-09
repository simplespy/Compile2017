package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.Scope;
import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/24.
 */
public abstract class Node {
    public String name;
    public Location loc;
    public Scope scope;
    public TypeNode type;
    public boolean isLv;

    public Node(){
    }
    abstract public void accept(ASTVisitor visitor);

    public Location getLoc() {
        return loc;
    }

    public String getName() {
        return name;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    public boolean isLoadable() {
        if (this.type instanceof ArrayType) return false;
        return true;
    }



    public TypeNode getType() {
      //  System.out.println("Not Override getType()");
        return type;
    }
    public MemoryReference getMemoryReference(){
        return null;
    }
    public Operand getOperand() {
        return null;
    }

    public Symbol getAddress(){
        return null;
    }
    public boolean isConstant(){return false;}
    public VarDecNode getVardec() {
        return null;
    }


}
