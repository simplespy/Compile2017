package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.FrontEnd.LocalScope;
import simplespy.compiler2017.NodeFamily.Location;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/12.
 */
public class Assign extends Stmt {
    public Expr lhs, rhs;
    public Location loc;

    public Assign (Location loc, Expr lhs, Expr rhs){
        this.loc = loc;
        this.lhs = lhs;
        this.rhs = rhs;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public Location getLoc() {
        return loc;
    }

    public Expr getLhs() {
        return lhs;
    }

    public Expr getRhs() {
        return rhs;
    }
}
