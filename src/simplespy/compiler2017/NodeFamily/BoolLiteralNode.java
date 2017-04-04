package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class BoolLiteralNode extends ExprNode {
    public final boolean value;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }
    public BoolLiteralNode(boolean value, Location loc) {
        this.value = value;
        this.loc = loc;
        isLv = false;
        this.type = new BaseType(TypeNode.TYPENAME.BOOL, loc);
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
