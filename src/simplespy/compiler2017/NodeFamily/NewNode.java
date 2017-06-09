package simplespy.compiler2017.NodeFamily;


import simplespy.compiler2017.FrontEnd.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spy on 17/3/27.
 */
public class NewNode extends ExprNode {
    public TypeNode type;
    public final List<ExprNode> item;
    public Location coordidnate;

    public NewNode(TypeNode type) {
        this.type = type;
        this.item = new ArrayList<>();
    }

    public NewNode(TypeNode type, List<ExprNode> item) {
        this.type = type;
        this.item = item;
    }

    public void add(Object node) {
        if (node instanceof ExprNode) item.add((ExprNode) node);
        else if (node == null) item.add(null);
        else throw new RuntimeException("Invalid type");
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
        return type;
    }

}
