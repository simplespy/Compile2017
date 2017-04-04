package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/24.
 */
public class IfNode extends StmtNode {
    public  ExprNode condition;
    public  StmtNode then;
    public  StmtNode otherwise;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }

    public IfNode(ExprNode condition, StmtNode then, StmtNode otherwise, Location loc) {
        this.condition = condition;
        this.then = then;
        this.otherwise = otherwise;
        this.loc = loc;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }


}
