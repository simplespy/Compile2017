package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spy on 17/3/27.
 */
public class BlockNode extends StmtNode {
    public final List<StmtNode> stmts;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }

    public BlockNode(Location loc){
        this.loc = loc;
        stmts = new ArrayList<>();
    }

    public BlockNode(List<StmtNode> stmts, Location loc) {
        this.stmts = stmts;
        this.loc = loc;
    }

    public void add(Object node) {
        if (node instanceof StmtNode) stmts.add((StmtNode) node);
        else if (node instanceof List) ((List) node).stream().forEachOrdered(this::add);
    }

    public List<StmtNode> getStmts() {
        return stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
