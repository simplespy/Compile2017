package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 5/17/17.
 */
public class Addr extends Expr {
    public Node entity;

    public Addr(Node ent) {
        this.entity = ent;
    }

    public Var refNode() {
        return new Var(entity);
    }
    public Node getEntityForce(){
        return entity;
    }    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public MemoryReference getMemoryReference(){
        return entity.getMemoryReference();
    }
    public Symbol getAddress(){
        return entity.getAddress();
    }
    public boolean isAddr() { return true; }

}
