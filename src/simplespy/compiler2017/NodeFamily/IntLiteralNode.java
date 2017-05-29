package simplespy.compiler2017.NodeFamily;


import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/26.
 */
public class IntLiteralNode extends ExprNode {
    public int value;
    public TypeNode type;

    public IntLiteralNode(int value){
        this.value = value;
        type = new BaseType(TypeNode.TYPENAME.INT, null);
    }

    @Override
    public TypeNode getType() {
        return type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return false;
    }
    public boolean isConstant(){return true;}

}
