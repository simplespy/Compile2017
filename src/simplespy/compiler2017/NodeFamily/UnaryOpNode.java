package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/25.
 */
public class UnaryOpNode extends ExprNode {
    public enum UnaryOp {
        INC, DEC, POS, NEG, LOGICAL_NOT, BITWISE_NOT
    }

    public UnaryOp op;
    public ExprNode body;

    public UnaryOpNode(UnaryOp op, ExprNode body){
        this.op = op;
        this.body = body;
    }

    public UnaryOp getOp() {
        return op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return false;
    }

    @Override
    public TypeNode getType() {
        return body.getType();
    }
}
