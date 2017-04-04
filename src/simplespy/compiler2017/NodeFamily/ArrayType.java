package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class ArrayType extends TypeNode {
    public TypeNode baseType;
    public int dimension;
    public final Location loc;

    public Location getLoc() {
        return loc;
    }

    public ArrayType(TypeNode baseType, int dimension, Location loc){
        this.baseType = baseType;
        this.dimension = dimension;
        this.loc = loc;
    }

    public TypeNode getLastType(){
        if (getDimension() == 1)
            return baseType;
        else return new ArrayType(baseType, getDimension()-1, getLoc());
    }

    public void incDimension() {
        ++this.dimension;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public TypeNode getBaseType() {
        return baseType.getBaseType();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
