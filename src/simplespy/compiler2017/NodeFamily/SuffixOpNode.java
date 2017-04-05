package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class SuffixOpNode extends ExprNode {
    public final UnaryOpNode.UnaryOp op;
    public final ExprNode expr;


    public SuffixOpNode(UnaryOpNode.UnaryOp op, ExprNode expr, Location loc) {
        this.op = op;
        this.expr = expr;
        this.loc = loc;
        isLv = false;
    }

    public UnaryOpNode.UnaryOp getOp() {
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
        return expr.getType();
    }
}
