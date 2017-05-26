package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class MemberNode extends ExprNode {
    public IDNode member;
    public final ExprNode expr;
    public Location loc;

    public MemberNode(ExprNode expr,IDNode member) {
        this.member = member;
        this.expr = expr;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return expr.Lv();
    }

    @Override
    public TypeNode getType() {
        return this.member.getType();
    }
}
