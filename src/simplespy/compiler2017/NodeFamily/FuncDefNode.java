package simplespy.compiler2017.NodeFamily;


import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.NodeFamily.IRNode.Stmt;

import java.util.List;

/**
 * Created by spy on 17/3/25.
 */
public class FuncDefNode extends ASTBranch {
    public final TypeNode returnType;
    public final String name;
    public final List<VarDecNode> parameters;
    public final BlockNode body;
    public final Location loc;
    public ReturnNode returnNode;

    public ClassDefNode externClass = null;

    public List<Stmt> ir;


    public Location getLoc() {
        return loc;
    }

    public FuncDefNode(TypeNode returnType, String name, List<VarDecNode> parameters, BlockNode body, Location loc) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.body = body;
        this.loc = loc;
        this.returnNode = null;
    }

    public String getName() {
        return name;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public TypeNode getType() {
        return returnType;
    }

    public void setIr(List<Stmt> ir) {
        this.ir = ir;
    }

    public List<Stmt> getIr() {
        return ir;
    }
}
