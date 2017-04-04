package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/25.
 */
public class BreakNode extends StmtNode {
    public final Location loc;

    public BreakNode(Location loc) {
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }


}
