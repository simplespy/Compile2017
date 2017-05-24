package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class BaseType extends TypeNode {
    public TYPENAME type;
    public final Location loc;
    int length;


    public Location getLoc() {
        return loc;
    }

    public BaseType(TYPENAME type, Location loc) {
        this.type = type;
        this.loc = loc;
        this.length = 1;
    }

    @Override
    public int getLen() {
        return length;
    }

    @Override
    public TypeNode getBaseType() {
        return this;
    }

    @Override
    public int getDim() {
        return 0;
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
