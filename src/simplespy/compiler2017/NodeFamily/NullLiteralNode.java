package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class NullLiteralNode extends ExprNode {
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public NullLiteralNode(){
        isLv = false;
    }

    @Override
    public boolean Lv() {
        return false;
    }
    public boolean isConstant(){return true;}

}
