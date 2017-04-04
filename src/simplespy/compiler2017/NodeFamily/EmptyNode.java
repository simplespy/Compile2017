package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class EmptyNode extends ExprNode {
    public Location coordinate;
    public final Location loc;

    public EmptyNode(Location loc) {
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return false;
    }
}
