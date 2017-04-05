package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;

import java.util.Stack;

/**
 * Created by spy on 17/4/1.
 */
public class DereferenceChecker implements ASTVisitor {
    private TypeTable typeTable;
    private GlobalScope globalScope;

    private Stack<Node> loopStack;
    private FuncDefNode currentFunction;
    private FuncDefNode currentParameter;


    public DereferenceChecker(){
        loopStack = new Stack<>();
    }

    private boolean checkType(TypeNode A, TypeNode B){
        if (A instanceof ArrayType && B == null) return true;
        if (A instanceof ClassType && B == null) return true;
        if (A instanceof ArrayType && B instanceof ArrayType){
            return(checkType(((ArrayType) A).getLastType(), ((ArrayType) B).getLastType()));
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
      /*  if (!node.getName().equals("main") && node.returnNode == null && !node.returnType.toString().equals("VOID")){
            CompilationError.exceptions.add(new SemanticException("Function doesn't return"));
        }*/
        currentFunction = null;
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
           if (x instanceof ReturnNode && ((ReturnNode) x).value != null){
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
            if (!checkType(node.getType(), node.vardec.init.getType())){
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
        if (!checkType(currentFunction.returnType, retType)) {
            CompilationError.exceptions.add(new SemanticException("Unmatched Return Type" + node.getLoc().toString()));
            return;
        }
        currentFunction.returnNode = node;

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
        if (!checkType(node.getLeft().getType(), node.getRight().getType())){
            CompilationError.exceptions.add(new SemanticException("Unmatched Binary Expression " + node.getLoc().toString()));
        }
        switch (node.getOp()){
            case ASSIGN:
                if (!node.getLeft().Lv()){
                    CompilationError.exceptions.add(new SemanticException("Expression is not left value" + node.getLoc().toString()));
                    return;
                }
                break;
            case LOGICAL_AND: case LOGICAL_OR:
                if (!node.getLeft().getType().toString().equals("BOOL") || !node.getRight().getType().toString().equals("BOOL")) {
                    CompilationError.exceptions.add(new SemanticException("Operation number must be INT" + node.getLoc().toString()));
                    return;
                }
                break;
            case EQ: break;
            case ADD:case LT:case GT:
                if (node.getLeft().getType().toString().equals("STRING") && node.getRight().getType().toString().equals("STRING")) {
                    break;
                }
            default:
                if (!node.getLeft().getType().toString().equals("INT") || !node.getRight().getType().toString().equals("INT")) {
                    CompilationError.exceptions.add(new SemanticException("Operation number must be INT"+ node.getLoc().toString()));
                    return;
                }

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

    }

    @Override
    public void visit(IDNode node) {
        try {
            Node ent = node.scope.get(node.name);
            node.setEntity(ent);
            node.type = ent.getType();
            if (ent instanceof FuncDefNode) currentParameter = (FuncDefNode) ent;
        }catch (Exception whatever){
            CompilationError.exceptions.add( new SemanticException("ID-Node Error at " + node.getLoc().toString()));
        }

    }

    @Override
    public void visit(ArefNode node) {
        if ((node.getExpr() instanceof NewNode)){
            CompilationError.exceptions.add( new SemanticException("Aref Error at " + node.getLoc().toString()));

        }

    }

    @Override
    public void visit(MemberNode node) {
        visit(node.expr);

        //if (node.expr instanceof ThisNode) {
            visit(node.member);

      //  }


    }

    @Override
    public void visit(ThisNode node) {

    }

    @Override
    public void visit(FuncallNode node) {
        visit(node.name);
        if (currentParameter.parameters != null && node.parameters != null){
            if (currentParameter.parameters.size() != node.parameters.size()){
                CompilationError.exceptions.add(new SemanticException("Unmatched Function Parameters number" + node.getLoc().toString()));
                return;
            }
            for (int i = 0; i < node.parameters.size(); ++i){
                if (!checkType(currentParameter.parameters.get(i).getType(), node.parameters.get(i).getType())){
                    CompilationError.exceptions.add(new SemanticException("Unmatched Function Parameters Type"+ node.getLoc().toString()));

                }
            }
        }
        else if (currentParameter.parameters == null && node.parameters == null){
            return;
        }
        else  CompilationError.exceptions.add(new SemanticException("Unmatched Function Parameters number"+ node.getLoc().toString()));


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
