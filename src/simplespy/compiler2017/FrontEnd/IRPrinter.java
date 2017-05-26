package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.NodeFamily.FuncDefNode;
import simplespy.compiler2017.NodeFamily.IRNode.*;
import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.io.PrintStream;

/**
 * Created by spy on 5/15/17.
 */
public class IRPrinter implements IRVisitor {
    StringBuilder indent = new StringBuilder();
    private void inc(){
        indent.append('\t');
    }
    private void dec(){
        indent.deleteCharAt(indent.length()-1);
    }
    PrintStream out;

    public IRPrinter(PrintStream out){
        this.out = out;
    }


    @Override
    public void visit(ExprStmt node) {
        visit(node.getExpr());

    }

    @Override
    public void visit(Bin node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "Op : " + node.getOp().toString());
        out.println(indent.toString() + "Left : ");
        inc();
        visit(node.getLeft());
        dec();
        out.println(indent.toString() + "Right : ");
        inc();
        visit(node.getRight());
        dec();
    }

    @Override
    public void visit(Call node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "Args : " );
        inc();
        node.getArgs().stream().forEachOrdered(this::visit);
        dec();

        out.println(indent.toString() + "Name : " );

        inc();
        visit(node.getName());
        dec();

    }

    @Override
    public void visit(CJump node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        out.println(indent.toString() + "If : " );
        inc();
        visit(node.getCond());
        dec();
        out.println(indent.toString() + "Then : " );
        inc();
        out.println(indent.toString() + "<<" + node.getThen().getClass().getSimpleName() + ">>" );
        dec();
        out.println(indent.toString() + "Else : " );

        inc();
        out.println(indent.toString() + "<<" + node.getOtherwise().getClass().getSimpleName() + ">>" );

        dec();
    }

    @Override
    public void visit(Expr node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(Int node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "Value : " + node.getValue());
    }

    @Override
    public void visit(IRRoot node) {
        if (node == null) return;
        out.println("---------------------------- ");
        out.println("Aha!I'm IR!");
        out.println("---------------------------- ");
        out.println("<<IR>> ");
        inc();
        out.println("Variables: ");
        if (node.vars != null) node.vars.stream().forEachOrdered(this::visit);
        out.println("Functions: ");
        node.funcs.values().stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(Jump node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );

    }

    @Override
    public void visit(LabelStmt node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "Name : " + node.label.getSymbol().name);

    }

    @Override
    public void visit(Return node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        out.println(indent.toString() + "Expr : " );
        inc();
        visit(node.getExpr());
        dec();

    }

    @Override
    public void visit(Str node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "Value : " + node.getValue());
    }

    @Override
    public void visit(Uni node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "Op : " + node.getOp().toString());
        out.println(indent.toString() + "Expr : ");
        inc();
        visit(node.getExpr());
        dec();

    }

    @Override
    public void visit(Var node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>");
        out.println(indent.toString() + "Name : " + node.entity.getName());
    }

    @Override
    public void visit(VarDecNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        out.println(indent.toString() + "Name : " + node.getName());
    }

    @Override
    public void visit(Assign node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        out.println(indent.toString() + "LHS : ");
        inc();
        visit(node.getLhs());
        dec();
        out.println(indent.toString() + "RHS : ");
        inc();
        visit(node.getRhs());
        dec();
    }

    @Override
    public void visit(FuncDefNode node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" + node.getLoc().toString());
        out.println(indent.toString() + "Name : " + node.getName());
        inc();
        node.ir.stream().forEachOrdered(this::visit);
        dec();
    }
    @Override
    public void visit(Stmt node) {
        node.accept(this);
    }

    @Override
    public void visit(Addr node) {
        if (node == null) return;
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>");
        out.println(indent.toString() + "Name : " + node.entity.getName());
    }

    @Override
    public void visit(Mem node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );


    }

    @Override
    public void visit(Malloc node) {
        out.println(indent.toString() + "<<" + node.getClass().getSimpleName() + ">>" );
        visit(node.spaceSize);

    }
}
