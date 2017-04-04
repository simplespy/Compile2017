package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by spy on 17/4/1.
 */
public class DereferenceChecker implements ASTVisitor {
    private TypeTable typeTable;
    private GlobalScope globalScope;

    private Stack<Node> loopStack;
    private FuncDefNode currentFunction;

    public DereferenceChecker(){
        loopStack = new Stack<>();
    }

    private boolean checkType(TypeNode A, TypeNode B){
        if (A instanceof ArrayType && B == null) return true;
        if (A instanceof ClassType && B == null) return true;
        if (A instanceof ArrayType && B instanceof ArrayType){
            return(A.getBaseType().toString().equals(B.getBaseType().toString())) && (((ArrayType) A).dimension == ((ArrayType) B).dimension);
        }

        return (A.toString().equals(B.toString()));
    }


    @Override
    public void visit(ASTRoot node) {
        typeTable = node.typeTable;
        globalScope = node.globalScope;
        node.branches.stream().forEachOrdered(this::visit);

    }

    @Override
    public void visit(ASTBranch node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ClassDefNode node) {
        node.getMembers().stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(FuncDefNode node) {
        currentFunction = node;
        visit(node.body);
    }

    @Override
    public void visit(VarDecNode node) {

    }

    @Override
    public void visit(TypeNode node) {
        if (node == null) return;
        node.accept(this);
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
        node.body.getStmts().stream().forEachOrdered(x->{
            if (x instanceof ReturnNode){
                CompilationError.exceptions.add(new SemanticException("Return in Constructor " + node.getLoc().toString()));
            }
        });
    }

    @Override
    public void visit(StmtNode node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(BlockNode node) {
        node.getStmts().stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(VarDecInBlockNode node) {
        if (node.vardec.init != null){
            visit(node.vardec.init);
            if (!checkType(node.vardec.init.getType(), node.getType())){
                CompilationError.exceptions.add(new SemanticException("Unmatched Variable Initialization"));
                System.exit(1);
            }
        }


    }

    @Override
    public void visit(WhileNode node)   {
        loopStack.push(node);
        if (!node.condition.getType().getTypeName().toString().equals("BOOL")){
            CompilationError.exceptions.add(new SemanticException("Condition is not BOOL " + node.getLoc().toString()));
        }
        visit(node.condition);
        visit(node.body);
        loopStack.pop();
    }

    @Override
    public void visit(ForNode node) {
        loopStack.push(node);

        visit(node.init);
        if (node.condition == null) {
            visit(node.step);
            loopStack.pop();
            return;
        }
        if (node.condition.getType().toString() != "BOOL"){
            CompilationError.exceptions.add(new SemanticException("Condition is not BOOL " + node.getLoc().toString()));
        }
        visit(node.condition);
        visit(node.step);
        loopStack.pop();


    }

    @Override
    public void visit(ReturnNode node) {
        if (currentFunction == null) {
            CompilationError.exceptions.add(new SemanticException( "Return out of function" + node.getLoc().toString()));
            return;
        }
        TypeNode retType;
        if (node.value != null) {
            visit(node.value);
            retType = node.value.getType();
        } else {
            retType = new BaseType(TypeNode.TYPENAME.VOID, node.getLoc());
        }
        if (!checkType(retType, currentFunction.returnType)) {
            CompilationError.exceptions.add(new SemanticException("Unmatched Return Type" + node.getLoc().toString()));
            return;
        }

    }

    @Override
    public void visit(IfNode node) {
        if (!node.condition.getType().toString().equals("BOOL")){
            CompilationError.exceptions.add(new SemanticException("Condition is not BOOL " + node.getLoc().toString()));
        }
        visit(node.condition);
        visit(node.then);
        visit(node.otherwise);
    }

    @Override
    public void visit(BreakNode node) {
        if (loopStack.isEmpty()){
            CompilationError.exceptions.add(new SemanticException("Break not in loop"));
        }

    }

    @Override
    public void visit(ContinueNode node) {
        if (loopStack.isEmpty()){
            CompilationError.exceptions.add(new SemanticException("Continue not in loop"));
        }
    }

    @Override
    public void visit(ExprNode node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(BinaryOpNode node) {
        visit(node.getLeft());
        visit(node.getRight());
        if (node.getOp() == BinaryOpNode.BinaryOp.ASSIGN && !node.getLeft().Lv()) {
            System.out.println(node.getLeft().Lv());
            CompilationError.exceptions.add(new SemanticException("Expression is not left value" + node.getLoc().toString()));
            return;
        }
        if (!checkType(node.getLeft().getType(), node.getRight().getType())){
            CompilationError.exceptions.add(new SemanticException("Unmatched Binary Expression " + node.getLoc().toString()));
        }
    }

    @Override
    public void visit(UnaryOpNode node) {
        visit(node.body);
        switch (node.op) {
            case INC:
            case DEC:
                if (!node.body.Lv()) {
                    CompilationError.exceptions.add(new SemanticException("Unary value must be Lvalue."));
                    return;
                }
                if (node.body.getType().toString() != "INT") {
                    CompilationError.exceptions.add(new SemanticException("Unary Operation number must be INT"));
                    return;
                }
                node.type = new BaseType(TypeNode.TYPENAME.INT, node.getLoc());
                node.isLv = true;
                return;

            case POS:
            case NEG:
            case BITWISE_NOT:
                if (node.body.getType().toString() != "INT") {
                    CompilationError.exceptions.add(new SemanticException( "Unary Operation number must be INT "));
                    return;
                }
                node.type = new BaseType(TypeNode.TYPENAME.INT, node.getLoc());
                node.isLv = false;
                return;

            case LOGICAL_NOT:
                if (node.body.getType().toString() != "BOOL") {
                    CompilationError.exceptions.add(new SemanticException( "Logical Operation number must be BOOL "));
                    return;
                }
                node.type = new BaseType(TypeNode.TYPENAME.BOOL, node.getLoc());
                node.isLv = false;
                return;
        }
    }

    @Override
    public void visit(SuffixOpNode node) {
        if (!node.expr.Lv()) {
            CompilationError.exceptions.add(new SemanticException("Expression is not left value" + node.getLoc().toString()));
            return;
        }
        visit(node.expr);
        if (node.expr.getType().toString() != "INT") {
            CompilationError.exceptions.add(new SemanticException("Suffix Operation number must be INT"));
            return;
        }
    }

    @Override
    public void visit(NewNode node) {
        TypeNode type = node.getType();

        for (int i = 0; i < node.item.size(); ++i) {
            ExprNode it = node.item.get(i);
            if (it == null) break;
            visit(it);
            if (it.getType().toString() != "INT") {
                CompilationError.exceptions.add(new SemanticException("Dimension expression in a new-expression should be INT"));
                return;
            }
        }
        /*for (int i = 0; i < node.item.size(); ++i)
            type = new ArrayType(type, i+1, node.getLoc());

        node.type = type;*/
        if (node.item.size() != 0) type = new ArrayType(type, node.item.size(), node.getLoc());
        node.type = type;

    }

    @Override
    public void visit(IDNode node) {
        try {
            Node ent = node.scope.get(node.name);
            ent.referred();
            node.setEntity(ent);
            node.type = ent.getType();
        }catch (Exception whatever){
            CompilationError.exceptions.add( new SemanticException("ID-Node Error at " + node.getLoc().toString()));
        }

    }

    @Override
    public void visit(ArefNode node) {

    }

    @Override
    public void visit(MemberNode node) {
        visit(node.expr);

        if (node.expr instanceof ThisNode) {
            visit(node.member);

        }

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
