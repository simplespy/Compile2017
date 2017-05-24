package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.UnaryOpNode;

/**
 * Created by spy on 17/4/15.
 */
public class Uni extends Expr {
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }



    protected UnaryOpNode.UnaryOp op;
    protected Expr expr;
    private Int operand;

    public Expr getExpr() {
        return expr;
    }

    public Uni(UnaryOpNode.UnaryOp op, Expr expr) {
        this.op = op;
        this.expr = expr;
    }

    public Int getOperand() {
        return operand;
    }

    public UnaryOpNode.UnaryOp getOp() {
        return op;
    }
}
