package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;

import java.io.PrintStream;

/**
 * Created by spy on 17/3/26.
 */
public class ASTPrinter implements ASTVisitor {
    StringBuilder indent = new StringBuilder();
    private void inc(){
        indent.append('\t');
    }
    private void dec(){
        indent.deleteCharAt(indent.length()-1);
    }
    PrintStream out;

    public ASTPrinter(PrintStream out){
        this.out = out;
    }

    @Override
    public void visit(ASTRoot node){
        if (node == null) return;
        out.println("---------------------------- ");
        out.println("Aha!ProgramStart! <(￣︶￣)> ");
        out.println("---------------------------- ");
        out.println("<<ASTRoot>> ");


        inc();
        node.branches.stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(ASTBranch node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ClassDefNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Name : " + node.getName());
        inc();
        node.members.stream().forEachOrdered(this::visit);
        dec();
    }

    @Override
    public void visit(FuncDefNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Name : " + node.getName());
        out.println(indent.toString() + "ReturnType : " + node.getReturnType().toString());
        out.println(indent.toString() + "Parameters : ");
        inc();
        node.parameters.stream().forEachOrdered(this::visit);
        dec();
        out.println(indent.toString() + "body : ");
        inc();
        visit(node.body);
        dec();
    }

    @Override
    public void visit(VarDecNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        out.println(indent.toString() + "Name : " + node.getName());
        out.println(indent.toString() + "Type : ");
        inc();
        visit(node.type);
        dec();
        out.println(indent.toString() + "Init : ");
        inc();
        visit(node.init);
        dec();
    }

    @Override
    public void visit(ConstructorNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Name : " + node.getName());
        out.println(indent.toString() + "body : ");
        inc();
        visit(node.body);
        dec();
    }

    @Override
    public void visit(StmtNode node)  {
        if (node == null) return;
        node.accept(this);
    }
    
    

    @Override
    public void visit(BlockNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        inc();
        node.stmts.stream().forEachOrdered(this::visit);
        dec();
    }

    @Override
    public void visit(WhileNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "condition : ");
        inc();
        visit(node.condition);
        dec();
        out.println(indent.toString() + "body : ");
        inc();
        visit(node.body);
        dec();
    }

    @Override
    public void visit(ForNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        out.println(indent.toString() + "Init : ");
        inc();
        visit(node.init);
        dec();
        out.println(indent.toString() + "Condition : ");
        inc();
        visit(node.condition);
        dec();
        out.println(indent.toString() + "Step : ");
        inc();
        visit(node.step);
        dec();
    }

    @Override
    public void visit(ReturnNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Expr : " );
        inc();
        visit(node.value);
        dec();
    }

    @Override
    public void visit(TypeNode node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ArrayType node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Dimension : " + node.getDimension());
        out.println(indent.toString() + "BaseType : ");
        inc();
        visit(node.baseType);
        dec();
    }

    @Override
    public void visit(BaseType node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Type : " + node.toString());
    }

    @Override
    public void visit(ClassType node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Type : " + node.toString());
    }

    @Override
    public void visit(VarDecInBlockNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        inc();
        visit(node.vardec);
        dec();
    }

    @Override
    public void visit(IfNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "if : ");
        inc();
        visit(node.condition);
        dec();
        out.println(indent.toString() + "then : ");
        inc();
        visit(node.then);
        dec();
        out.println(indent.toString() + "otherwise : ");
        inc();
        visit(node.otherwise);
        dec();
    }

    @Override
    public void visit(BreakNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
    }

    @Override
    public void visit(ContinueNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
    }


    @Override
    public void visit(ExprNode node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(BinaryOpNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Operator : " + node.getOp());
        out.println(indent.toString() + "Left:");
        inc();
        visit(node.getLeft());
        dec();
        out.println(indent.toString() + "Right:");
        inc();
        visit(node.getRight());
        dec();
    }

    @Override
    public void visit(UnaryOpNode node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>");;
        out.println(indent.toString() + "Operator : " + node.getOp().toString());
        out.println(indent.toString() + "Expr : ");
        inc();
        visit(node.body);
        dec();
        
        
    }

    @Override
    public void visit(SuffixOpNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );;
        out.println(indent.toString() + "Operator : " + node.getOp().toString());
        out.println(indent.toString() + "Expr : ");
        inc();
        visit(node.expr);
        dec();

    }

    @Override
    public void visit(NewNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );;
        visit(node.type);
        inc();
        node.item.stream().forEachOrdered(this::visit);
        dec();

    }

    @Override
    public void visit(IDNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Name : " + node.getName());
    }

    @Override
    public void visit(ArefNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Array : ");
        inc();
        visit(node.expr);
        dec();
        inc();
        visit(node.index);
        dec();
    }

    @Override
    public void visit(ThisNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );;
    }



    @Override
    public void visit(MemberNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );;
        out.println(indent.toString() + "Expr : ");
        inc();
        visit(node.expr);
        dec();
        out.println(indent.toString() + "Member : ");
        inc();
        visit(node.member);
        dec();
    }

    @Override
    public void visit(FuncallNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Name : ");
        inc();
        visit(node.name);
        dec();
        out.println(indent.toString() + "Parameters : ");
        inc();
        node.parameters.stream().forEachOrdered(this::visit);
        dec();
    }

    @Override
    public void visit(IntLiteralNode node){
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );;
        out.println(indent.toString() + "Value: " + node.value);
    }

    @Override
    public void visit(BoolLiteralNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
        out.println(indent.toString() + "Value: " + node.value);
    }

    @Override
    public void visit(EmptyNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());;
    }

    @Override
    public void visit(NullLiteralNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>");;
    }

    @Override
    public void visit(StringLiteralNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>");;
        out.println(indent.toString() + "Value: " + node.value);
    }
}
