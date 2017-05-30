package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.ClassDefNode;
import simplespy.compiler2017.NodeFamily.Node;

import java.util.List;

/**
 * Created by spy on 17/4/15.
 */
public class Call extends Expr {
    public Expr name;
    private List<Expr> args;
    public Expr argThis = null;
    public Call(Expr name, List<Expr> args){
        this.name = name;
        this.args = args;
    }
    public ClassDefNode classEntity;

    public Expr getName() {
        return name;
    }

    public List<Expr> getArgs() {
        return args;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public Node getEntityForce(){
        return name.getEntityForce();
    }
}
