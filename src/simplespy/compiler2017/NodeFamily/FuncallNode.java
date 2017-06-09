package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

import java.util.List;

/**
 * Created by spy on 17/3/24.
 */
public class FuncallNode extends ExprNode {
    public final ExprNode name;
    public final List<ExprNode> parameters;
    public final Location loc;
    public FuncDefNode entity;

    public Location getLoc() {
        return loc;
    }
    public FuncallNode(ExprNode name, List<ExprNode> parameters, Location loc) {
        this.name = name;
        this.parameters = parameters;
        this.loc = loc;
    }

    @Override
    public boolean Lv() {
        return false;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TypeNode getType() {
        return name.getType();
    }



}
