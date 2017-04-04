package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/25.
 */
public class ReturnNode extends StmtNode{
    public ExprNode value;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }
    public ReturnNode(ExprNode value, Location loc){
        this.value = value;
        this.loc = loc;
    }


    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
