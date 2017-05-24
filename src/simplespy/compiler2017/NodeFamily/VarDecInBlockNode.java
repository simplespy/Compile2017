package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class VarDecInBlockNode extends StmtNode {
    public final VarDecNode vardec;
    public final Location loc;

    public VarDecInBlockNode(VarDecNode vardec, Location loc) {
        this.vardec = vardec;
        this.loc = loc;
    }


    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public Location getLoc() {
        return loc;
    }

    public VarDecNode getVardec() {
        return vardec;
    }

    @Override
    public String getName() {
        return vardec.getName();
    }

    @Override
    public TypeNode getType() {
        return vardec.getType();
    }

    @Override
    public MemoryReference getMemoryReference() {
        return vardec.getMemoryReference();
    }
    public Symbol getAddress(){
        return vardec.getAddress();
    }
}
