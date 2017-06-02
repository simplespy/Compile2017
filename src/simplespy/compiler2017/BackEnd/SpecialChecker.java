package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.NodeFamily.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spy on 17/6/2.
 */
public class SpecialChecker implements ASTVisitor {
    @Override
    public void visit(ASTRoot node) {
        node.branches.stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(ASTBranch node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ClassDefNode node) {
        node.members.stream().forEachOrdered(this::visit);

    }

    @Override
    public void visit(FuncDefNode node) {
        visit(node.body);

    }

    @Override
    public void visit(VarDecNode node) {

    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(ArrayType node) {

    }

    @Override
    public void visit(ClassType node) {

    }

    @Override
    public void visit(BaseType node) {

    }

    @Override
    public void visit(ConstructorNode node) {

    }

    @Override
    public void visit(StmtNode node) {
        node.accept(this);

    }

    @Override
    public void visit(BlockNode node) {
        node.stmts.stream().forEachOrdered(this::visit);
        if (swapflag && node.stmts.get(0).equals(forNode) && !node.stmts.get(0).equals(ifNode)) {
            node.stmts.remove(0);
            node.stmts.add(ifNode);
            swapflag = false;
            swapdone = true;
        }
    }

    @Override
    public void visit(VarDecInBlockNode node) {

    }

    @Override
    public void visit(WhileNode node) {

    }
    List<Node> ForUsedVars = new ArrayList<>();
    ForNode forNode = null;
    IfNode ifNode = null;
    boolean swapflag = false;
    boolean swapdone = false;
    @Override
    public void visit(ForNode node) {
        ForUsedVars.clear();
        usedVarList.clear();
        forNode = node;
        final boolean[] flag = {true};
        visit(node.step);
        ForUsedVars.addAll(usedVarList);
        visit(node.init);
        ForUsedVars.addAll(usedVarList);
        visit(node.condition);
        ForUsedVars.addAll(usedVarList);
        node.ForUsedVars = ForUsedVars;

        visit(node.body);

        ForUsedVars = node.ForUsedVars;
        if (node.body instanceof IfNode && swapdone == false){
        }
        else if (swapdone == false && node.body instanceof BlockNode && ((BlockNode) node.body).stmts.size() == 1 && ((BlockNode) node.body).stmts.get(0) instanceof IfNode){
            visit(node.body);
            usedVarList.stream().forEachOrdered(x->{
                if (ForUsedVars.contains(x)) flag[0] = false;
            });
            if (flag[0]){
                forNode.body = ifNode.then;
                ifNode.then = forNode;
                swapflag = true;
            }
        }

    }

    @Override
    public void visit(ReturnNode node) {

    }

    @Override
    public void visit(IfNode node) {
        ifNode = node;
        visit(node.condition);

    }

    @Override
    public void visit(BreakNode node) {

    }

    @Override
    public void visit(ContinueNode node) {

    }

    @Override
    public void visit(ExprNode node) {
        node.accept(this);

    }
    List<Node> usedVarList = new ArrayList<>();
    @Override
    public void visit(BinaryOpNode node) {
        usedVarList.clear();
        if (node.getLeft() instanceof IDNode){
            usedVarList.add(((IDNode) node.getLeft()).getEntity());
        }
        if (node.getRight() instanceof IDNode){
            usedVarList.add(((IDNode) node.getRight()).getEntity());
        }
    }

    @Override
    public void visit(UnaryOpNode node) {

    }

    @Override
    public void visit(SuffixOpNode node) {

    }

    @Override
    public void visit(NewNode node) {

    }

    @Override
    public void visit(IDNode node) {

    }

    @Override
    public void visit(ArefNode node) {

    }

    @Override
    public void visit(MemberNode node) {

    }

    @Override
    public void visit(ThisNode node) {

    }

    @Override
    public void visit(FuncallNode node) {

    }

    @Override
    public void visit(IntLiteralNode node) {

    }

    @Override
    public void visit(BoolLiteralNode node) {

    }

    @Override
    public void visit(StringLiteralNode node) {

    }

    @Override
    public void visit(NullLiteralNode node) {

    }

    @Override
    public void visit(EmptyNode node) {

    }
}
