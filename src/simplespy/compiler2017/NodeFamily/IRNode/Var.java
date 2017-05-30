package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Node;


/**
 * Created by spy on 17/4/15.
 */
public class Var extends Expr {
    public Node entity;

    public Var(Node ent) {
        this.entity = ent;
    }

    public Addr addressNode() {
        return new Addr(entity);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public Node getEntityForce(){
        return entity;
    }

    public MemoryReference getMemoryReference(){
        return entity.getMemoryReference();
    }
    public boolean isVar(){return true;}

    @Override
    public boolean isString() {
        if (entity.getType().toString().equals("STRING")) return true;
        else return false;
    }
}
