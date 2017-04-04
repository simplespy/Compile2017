package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/25.
 */
public abstract class ASTBranch extends Node{


    public ASTBranch() {
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
