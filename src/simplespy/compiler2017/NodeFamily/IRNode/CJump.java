package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Location;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/12.
 */
public class CJump extends Stmt {
    Expr cond;
    Label then, otherwise;
    Location loc;
    public CJump(Location loc, Expr cond, Label then, Label otherwise){
        this.loc = loc;
        this.cond = cond;
        this.then = then;
        this.otherwise = otherwise;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public Location getLoc() {
        return loc;
    }

    public Expr getCond() {
        return cond;

    }

    public Label getOtherwise() {
        return otherwise;
    }

    public Label getThen() {
        return then;
    }
}
