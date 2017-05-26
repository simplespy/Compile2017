package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.NodeFamily.IRNode.Stmt;

import java.util.List;

/**
 * Created by spy on 17/3/30.
 */
public class ConstructorNode extends ASTBranch {
    public final String name;
    public final BlockNode body;
    public final Location loc;
    public List<Stmt> ir;

    public ConstructorNode(String name, BlockNode body, Location loc) {
        this.name = name;
        this.body = body;
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public Location getLoc() {
        return loc;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public void setIr(List<Stmt> ir) {
        this.ir = ir;
    }

    public List<Stmt> getIr() {
        return ir;
    }
}
