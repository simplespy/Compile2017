package simplespy.compiler2017.NodeFamily;


import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/25.
 */
public class VarDecNode extends ASTBranch {
    private String name;
    public TypeNode type;
    public ExprNode init;
    public final Location loc;

    public VarDecNode(TypeNode type, String name, ExprNode init, Location loc){
        this.type = type;
        this.name = name;
        this.init = init;
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public TypeNode getType() {
        return type;
    }
}
