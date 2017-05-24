package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;

/**
 * Created by spy on 5/15/17.
 */
abstract public class Stmt {
    public abstract void accept(IRVisitor visitor);
}
