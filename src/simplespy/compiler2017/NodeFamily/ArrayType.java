package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/29.
 */
public class ArrayType extends TypeNode {
    public TypeNode baseType;
    public ExprNode dimension;
    public final Location loc;
    int length;

    @Override
    public int getLen() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Location getLoc() {
        return loc;
    }

    public ArrayType(TypeNode baseType, ExprNode dimension, Location loc){
        this.baseType = baseType;
        this.dimension = dimension;
        this.loc = loc;
    }

    public TypeNode getLastType(){
        return baseType;
    }


    public ExprNode getDimension() {
        return dimension;
    }

    @Override
    public TypeNode getBaseType() {
        return baseType.getBaseType();
    }

    public int getDim(){
        return baseType.getDim() + 1;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
