package simplespy.compiler2017.NodeFamily;


import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/24.
 */
public class BinaryOpNode extends ExprNode {
    @Override
    public boolean Lv() {
        return false;
    }

    public enum BinaryOp {
        ASSIGN,
        LOGICAL_OR, LOGICAL_AND,
        BITWISE_OR, BITWISE_AND, XOR,
        EQ, NE, LT, GT, LE, GE,
        SHL, SHR,
        ADD, SUB,
        MUL, DIV, MOD
    }
    protected BinaryOp op;
    protected ExprNode left, right;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }


    public BinaryOpNode(ExprNode left, BinaryOp op, ExprNode right, Location loc) {
        super();
        this.op = op;
        this.left = left;
        this.right = right;
        this.loc = loc;

    }


    public BinaryOp getOp() {
        return op;
    }

    public ExprNode getLeft() {
        return left;
    }

    public ExprNode getRight() {
        return right;
    }

    public void setLeft(ExprNode left) {
        this.left = left;
    }

    public void setRight(ExprNode right) {
        this.right = right;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    private boolean checkType(TypeNode A, TypeNode B){
        if (A instanceof ArrayType && B == null) return true;
        if (A instanceof ClassType && B == null) return true;
        if (A instanceof ArrayType && B instanceof ArrayType){
            return(checkType(((ArrayType) A).getLastType(), ((ArrayType) B).getLastType()));
        }

        return (A.toString().equals(B.toString()));
    }

    @Override
    public TypeNode getType() {
            TypeNode l = getLeft().getType();
            TypeNode r  =getRight().getType();
            if(!checkType(l, r)){
                CompilationError.exceptions.add(new SemanticException("Unmatched Binary Expression" + loc.toString()));
                return null;
            }
            switch (getOp()){
                case EQ: case NE: case LT: case GT: case LE: case GE: return new BaseType(TypeNode.TYPENAME.BOOL, getLoc());
                default: return l;
            }



    }
}
