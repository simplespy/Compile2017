package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class StringLiteralNode extends ExprNode{
    public final String value;

    public StringLiteralNode(String value) {
        this.value = value;
        type = new BaseType(TypeNode.TYPENAME.STRING, null);
        isLv = false;
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
