package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Location;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/15.
 */
public class Return  extends Stmt{
    Expr expr;
    Location loc;

    public Return(Location loc, Expr expr) {
        this.loc = loc;
        this.expr = expr;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public Location getLoc() {
        return loc;
    }

    public Expr getExpr() {
        return expr;
    }
}
