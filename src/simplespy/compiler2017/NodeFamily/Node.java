package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.Scope;
import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/24.
 */
public abstract class Node {
    public String name;
    public Location loc;
    public Scope scope;
    public int nReferred;
    public TypeNode type;
    public boolean isLv;

    public Node(){
        nReferred = 0;
    }
    abstract public void accept(ASTVisitor visitor);

    public Location getLoc() {
        return loc;
    }

    public String getName() {
        return name;
    }

    public void setScope(Scope scope) {

        this.scope = scope;
    }

    public void referred() {
        nReferred++;
    }

    public TypeNode getType() {
      //  System.out.println("Not Override getType()");
        return type;
    }

}
