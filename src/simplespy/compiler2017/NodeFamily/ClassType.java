package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class ClassType extends TypeNode {
    public final String name;
    public final Location loc;
    int length;

    public Location getLoc() {
        return loc;
    }
    public ClassType(String name) {
        this.name = name;
        loc = null;
    }
    public ClassType(String name, Location loc) {
        this.name = name;
        this.loc = loc;
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
    public int getLen() {
        length = 1;
        return 1;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
