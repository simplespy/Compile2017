package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.NewNode;

/**
 * Created by spy on 5/25/17.
 */
public class Malloc extends Expr {
    NewNode entity;
    public Expr spaceSize;


    public void setEntity(NewNode entity) {
        this.entity = entity;
    }

    public NewNode getEntity() {
        return entity;
    }

    public Expr addressNode(){
        return this;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);    }
}
