package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Location;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/15.
 */
public class LabelStmt extends Stmt {
    public Label label;
    public Location loc;
    public LabelStmt(Location loc, Label label){
        this.loc = loc;
        this.label = label;
    }


    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

}
