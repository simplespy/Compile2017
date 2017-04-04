package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/25.
 */
public class ForNode extends StmtNode {
    public final ExprNode init;
    public final ExprNode condition;
    public final ExprNode step;
    public final StmtNode body;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }
    public ForNode(ExprNode init, ExprNode condition, ExprNode step, StmtNode body, Location loc) {
        this.init = init;
        this.condition = condition;
        this.step = step;
        this.body = body;
        this.loc = loc;
    }


    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }


}
