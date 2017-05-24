package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.Location;
import simplespy.compiler2017.NodeFamily.Node;

/**
 * Created by spy on 17/4/12.
 */
public class Jump extends Stmt {
    Label label;
    Location loc;
    public Jump(Location loc, Label label){
        this.label = label;
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }



}
