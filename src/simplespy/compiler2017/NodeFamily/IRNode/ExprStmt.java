package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Location;

/**
 * Created by spy on 5/15/17.
 */
public class ExprStmt extends Stmt {
    protected Expr expr;
    protected Location loc;
    public ExprStmt(Location loc, Expr expr){
        this.loc = loc;
        this.expr = expr;
    }

    public Location getLoc() {
        return loc;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

}
