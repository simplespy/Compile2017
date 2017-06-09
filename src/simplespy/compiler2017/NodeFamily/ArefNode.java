package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.FrontEnd.ASTVisitor;


/**
 * Created by spy on 17/3/27.
 */
public class ArefNode extends ExprNode {
    public final ExprNode expr, index;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }

    public ArefNode(ExprNode expr, ExprNode index, Location loc){
        this.expr = expr;
        this.index = index;
        this.loc = loc;
        isLv = true;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public ExprNode getIndex() {
        return index;
    }

    public boolean isLv;

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return this.isLv;
    }

    @Override
    public TypeNode getType() {
        TypeNode orig = this.expr.getType();
        if (!(orig instanceof ArrayType))
            CompilationError.exceptions.add(new SemanticException("Array reference error"));
        TypeNode newtype = ((ArrayType)orig).getLastType();
        return newtype;
    }

}
