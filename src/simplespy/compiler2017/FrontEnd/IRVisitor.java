package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.NodeFamily.FuncDefNode;
import simplespy.compiler2017.NodeFamily.IRNode.*;
import simplespy.compiler2017.NodeFamily.VarDecNode;

/**
 * Created by spy on 5/15/17.
 */
public interface IRVisitor {
    void visit(ExprStmt node);
    void visit(Bin node);
    void visit(Call node);
    void visit(CJump node);
    void visit(Expr node);
    void visit(Int node);
    void visit(IRRoot node);
    void visit(Jump node);
    void visit(LabelStmt node);
    void visit(Return node);
    void visit(Str node);
    void visit(Uni node);
    void visit(Var node);
    void visit(VarDecNode node);
    void visit(Assign node);
    void visit(FuncDefNode node);
    void visit(Stmt node);
    void visit(Addr node);
    void visit(Mem node);
    void visit(Malloc node);
    void visit(This node);
}
