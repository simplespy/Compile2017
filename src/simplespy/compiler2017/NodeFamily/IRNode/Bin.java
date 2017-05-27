package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.BinaryOpNode;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/15.
 */
public class Bin extends Expr {


    protected BinaryOpNode.BinaryOp op;
    protected Expr left, right;

    public Bin(BinaryOpNode.BinaryOp op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public BinaryOpNode.BinaryOp getOp() {
        return op;
    }
    public Node getEntityForce(){
        return left.getEntityForce();
    }
}
