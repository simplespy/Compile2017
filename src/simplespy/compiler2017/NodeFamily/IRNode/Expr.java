package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/15.
 */
abstract public class Expr   {
    public Expr addressNode(){
        return null;
    }
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
}
