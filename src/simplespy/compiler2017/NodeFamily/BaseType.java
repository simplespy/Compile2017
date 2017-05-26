package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class BaseType extends TypeNode {
    public TYPENAME type;
    public final Location loc;


    public Location getLoc() {
        return loc;
    }

    public BaseType(TYPENAME type, Location loc) {
        this.type = type;
        this.loc = loc;
    }

    @Override
    public TypeNode getBaseType() {
        return this;
    }



    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public String toString(){
        return type.toString();
    }


    public TYPENAME getTypeName() {
        return type;
    }
}
