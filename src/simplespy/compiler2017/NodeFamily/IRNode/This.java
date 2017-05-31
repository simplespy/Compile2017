package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.IRVisitor;

/**
 * Created by spy on 17/5/31.
 */
public class This extends Expr {
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
