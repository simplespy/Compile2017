package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Asm.AsmType;
import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.NodeFamily.IRNode.*;
import simplespy.compiler2017.Utils.ListUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by spy on 5/6/17.
 */
public class IRGenerator implements ASTVisitor {
    private TypeTable typeTable;
    public static IRRoot ir;

    public IRRoot getIR() {
        return ir;
    }
    public IRGenerator() {
        ir = new IRRoot();
    }


    @Override
    public void visit(ASTRoot node) {
        typeTable = node.typeTable;
        ir.scope = node.globalScope;
        ir.typeTable = node.typeTable;
        node.branches.stream().forEachOrdered(this::visit);
    }
    @Override
    public void visit(ASTBranch node) {
        if (node == null) return;
        node.accept(this);

    }

    @Override
    public void visit(ClassDefNode node) {
        node.getMembers().stream().filter(x->x instanceof FuncDefNode || x instanceof ConstructorNode).forEachOrdered(this::visit);
    }


    @Override
    public void visit(FuncDefNode node) {
        node.setIr(compileFunctionBody(node));
        ir.funcs.put(node.getName(), node);
    }
    @Override
    public void visit(VarDecNode node) {
        if (node.init != null) {
            node.setIr(transformExpr(node.init));
        }
        ir.vars.add(node);
    }

    List<Stmt> stmts;
    Stack<Scope> scopeStack;
    Stack<Label> breakStack;
    Stack<Label> continueStack;
    Expr returnExpr;

    private List<Stmt> compileFunctionBody(FuncDefNode node) {
        stmts = new ArrayList<>();
        scopeStack = new Stack<>();
        breakStack = new Stack<>();
        continueStack = new Stack<>();
        node.body.accept(this);
        return stmts;
    }





    @Override
    public void visit(TypeNode node) {}
    @Override
    public void visit(ArrayType node) {}
    @Override
    public void visit(ClassType node) {}
    @Override
    public void visit(BaseType node) {}
    @Override
    public void visit(ConstructorNode node) {
        String name = node.name+"_constructor";

        FuncDefNode consturctor = new FuncDefNode(null, name, null, node.body, node.getLoc());
        stmts = new ArrayList<>();
        scopeStack = new Stack<>();
        breakStack = new Stack<>();
        continueStack = new Stack<>();
        node.body.accept(this);
        node.setIr(stmts);
        consturctor.setIr(stmts);
        ir.funcs.put(name, consturctor);
    }

    @Override
    public void visit(StmtNode node) {
        node.accept(this);
    }

    @Override
    public void visit(BlockNode node) {
        scopeStack.push(node.scope);
        node.getStmts().stream().forEachOrdered(this::visit);
        scopeStack.pop();
    }
    @Override
    public void visit(VarDecInBlockNode node) {
        if (node.getVardec().init != null) {
            stmts.add(new Assign(node.getLoc(), new Addr(node.getVardec()), transformExpr(node.getVardec().init)));
        }
    }

    @Override
    public void visit(WhileNode node) {
        Label begLabel = new Label();
        Label endLabel = new Label();
        Label bodyLabel = new Label();
        stmts.add(new LabelStmt(null, begLabel));
        stmts.add(new CJump(node.getLoc(), transformExpr(node.condition), bodyLabel, endLabel));
        stmts.add(new LabelStmt(null, bodyLabel));
        continueStack.push(bodyLabel);
        breakStack.push(endLabel);
        node.body.accept(this);
        breakStack.pop();
        continueStack.pop();
        stmts.add(new Jump(null, begLabel));
        stmts.add(new LabelStmt(null, endLabel));

    }

    @Override
    public void visit(ForNode node) {
        Label begLabel = new Label();
        Label bodyLabel = new Label();
        Label contLabel = new Label();
        Label endLabel = new Label();

        visit(node.init);
        stmts.add(new LabelStmt(node.getLoc(),begLabel));
        stmts.add(new CJump(node.getLoc(), transformExpr(node.condition), bodyLabel, endLabel));

        stmts.add(new LabelStmt(null, bodyLabel));
        continueStack.push(contLabel);
        breakStack.push(endLabel);
        visit(node.body);
        breakStack.pop();
        continueStack.pop();
        stmts.add(new LabelStmt(null, contLabel));
        visit(node.step);
        stmts.add(new Jump(null, begLabel));
        stmts.add(new LabelStmt(null, endLabel));

    }

    @Override
    public void visit(ReturnNode node) {
        stmts.add(new Return(node.getLoc(), node.value == null ? null : transformExpr(node.value)));
    }

    @Override
    public void visit(IfNode node) {
        Label thenLabel = new Label();
        Label elseLabel = new Label();
        Label endLabel = new Label();
        Expr cond = transformExpr(node.condition);
        if (node.otherwise == null) {
            stmts.add(new CJump(node.getLoc(), cond, thenLabel, endLabel));
            stmts.add(new LabelStmt(null, thenLabel));
            node.then.accept(this);
            stmts.add(new LabelStmt(null, endLabel));
        } else {

            stmts.add(new CJump(node.getLoc(), cond, thenLabel, elseLabel));
            stmts.add(new LabelStmt(null, thenLabel));
            node.then.accept(this);
            stmts.add(new Jump(node.getLoc(), endLabel));
            stmts.add(new LabelStmt(null, elseLabel));
            node.otherwise.accept(this);
            stmts.add(new LabelStmt(null, endLabel));

        }

    }

    @Override
    public void visit(BreakNode node) {
        if (breakStack.isEmpty()) {
            throw new Error("break error");
        } else {
            stmts.add(new Jump(node.getLoc(), breakStack.peek()));
        }

    }

    @Override
    public void visit(ContinueNode node) {
        if (continueStack.isEmpty()) {
            throw new Error("continue error");
        } else {
            stmts.add(new Jump(node.getLoc(), continueStack.peek()));
        }
    }

    @Override
    public void visit(ExprNode node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(BinaryOpNode node) {

        Expr right = transformExpr(node.getRight());
        Expr left = transformExpr(node.getLeft());
        if (node.getOp().equals(BinaryOpNode.BinaryOp.LOGICAL_AND)){
            Label rightLabel = new Label();
            Label endLabel = new Label();
            VarDecNode var = tmpVar(node.type);

            stmts.add(new Assign(node.getLoc(), new Addr(var), left));
            stmts.add(new CJump(node.getLoc(), new Var(var), rightLabel, endLabel));
            stmts.add(new LabelStmt(node.getLoc(), rightLabel));
            stmts.add(new Assign(node.getLoc(), new Addr(var), right));
            stmts.add(new LabelStmt(node.getLoc(), endLabel));
            returnExpr = isStatement() ? null : new Var(var);
        }else if (node.getOp().equals(BinaryOpNode.BinaryOp.LOGICAL_OR)){
            Label rightLabel = new Label();
            Label endLabel = new Label();
            VarDecNode var = tmpVar(node.type);

            stmts.add(new Assign(node.getLoc(), new Addr(var), left));
            stmts.add(new CJump(node.getLoc(), new Var(var), endLabel, rightLabel));
            stmts.add(new LabelStmt(node.getLoc(), rightLabel));
            stmts.add(new Assign(node.getLoc(), new Addr(var), right));
            stmts.add(new LabelStmt(node.getLoc(), endLabel));
            returnExpr = isStatement() ? null : new Var(var);
        }else if (node.getOp().equals(BinaryOpNode.BinaryOp.ASSIGN)) {
            stmts.add(new Assign(node.getLoc(), left.addressNode(), right));
            returnExpr = null;
        }
        else returnExpr = new Bin(node.getOp(), left, right);
    }

    @Override
    public void visit(UnaryOpNode node) {
        if (node.getOp() == UnaryOpNode.UnaryOp.POS) {
            returnExpr = transformExpr(node.body);
        } else if (node.getOp() == UnaryOpNode.UnaryOp.INC) {
            Expr expr = transformExpr(node.body);
            stmts.add(new Assign(node.getLoc(), expr.addressNode(), new Bin(BinaryOpNode.BinaryOp.ADD, expr, new Int(1))));
            returnExpr = expr;
        } else if (node.getOp() == UnaryOpNode.UnaryOp.DEC) {
            Expr expr = transformExpr(node.body);
            stmts.add(new Assign(node.getLoc(), expr.addressNode(), new Bin(BinaryOpNode.BinaryOp.SUB, expr, new Int(1))));
            returnExpr = expr;
        }else
            returnExpr = new Uni(node.getOp(), transformExpr(node.body));

    }

    @Override
    public void visit(SuffixOpNode node) {
        Expr expr = transformExpr(node.expr);
        if (isStatement()){
            stmts.add(new Assign(node.getLoc(), expr.addressNode(), new Bin(BinaryOpNode.BinaryOp.ADD, expr, new Int(1))));
        }
        else {
            VarDecNode tmp = tmpVar(node.expr.getType());
            stmts.add(new Assign(node.getLoc(), new Addr(tmp), expr));
            BinaryOpNode.BinaryOp op = node.getOp().toString().equals("++") ? BinaryOpNode.BinaryOp.ADD : BinaryOpNode.BinaryOp.SUB;
            stmts.add(new Assign(node.getLoc(), expr.addressNode(), new Bin(op, expr, new Int(1))));
            returnExpr = new Var(tmp);
        }
    }


    @Override
    public void visit(NewNode node) {
        Malloc space = new Malloc();
        space.setEntity(node);
        final Expr[] base = {new Int(SIZE)};
        node.item.stream().filter(x->x != null).forEachOrdered(x-> {
            Expr total = new Bin(BinaryOpNode.BinaryOp.MUL, base[0], transformExpr(x));
            base[0] = total;
        });
        space.spaceSize = base[0];
        returnExpr = space;
    }

    @Override
    public void visit(IDNode node) {
        returnExpr = new Var(node.getEntity());
    }

    static final int SIZE = 8;

    @Override
    public void visit(ArefNode node) {
        Expr expr = transformExpr(node.getExpr());
        Expr offset = new Bin(BinaryOpNode.BinaryOp.MUL, new Int(SIZE), transformExpr(node.getIndex()));
        Bin addr = new Bin(BinaryOpNode.BinaryOp.ADD, expr, offset);
        returnExpr = new Mem(addr);
    }

    @Override
    public void visit(MemberNode node) {
        Expr expr = transformExpr(node.expr);


        if (node.member.name.equals("size")){
                returnExpr = expr;
        }
        else {
            if (node.member.entity != null && node.member.entity instanceof FuncDefNode){//function
                returnExpr =  new Var(node.member.entity);
            }else{
                TypeNode type = node.expr.getType();
                if (type instanceof ClassType){
                    String className = ((ClassType) type).name;
                    ClassDefNode classEntity = typeTable.getClassDefNode(className);
                    int offset = classEntity.getOffset(node.member);
                    Expr addr = new Bin(BinaryOpNode.BinaryOp.ADD, expr.addressNode(), new Int(SIZE * offset));
                    returnExpr = new Mem(addr);
                }

            }
        }
    }

    @Override
    public void visit(ThisNode node) {}

    @Override
    public void visit(FuncallNode node) {
        List<Expr> args = new ArrayList<>();
        ListUtils.reverse(node.parameters).stream().forEachOrdered(x -> args.add(0, transformExpr(x)));

        Expr call = new Call(transformExpr(node.name), args);
        if (isStatement()) {
            stmts.add(new ExprStmt(node.getLoc(), call));
        }
        else {
            VarDecNode tmp = tmpVar(node.getType());
            returnExpr = new Var(tmp);
            stmts.add(new Assign(node.getLoc(), returnExpr.addressNode(), call));
        }
    }


    @Override
    public void visit(IntLiteralNode node) {
        returnExpr = new Int(node.value);
    }

    @Override
    public void visit(BoolLiteralNode node) {
        if (node.value == true){
            returnExpr =  new Int(1);
        }else returnExpr = new Int(0);

    }

    @Override
    public void visit(StringLiteralNode node) {
        returnExpr = new Str(node.value);
    }

    @Override
    public void visit(NullLiteralNode node) {

    }

    @Override
    public void visit(EmptyNode node) {

    }


    private VarDecNode tmpVar(TypeNode t) {
        return scopeStack.peek().allocateTmp(t);
    }





    private int exprNestLevel = 0;

    private Expr transformExpr(ExprNode node) {
        if (node == null)
            return null;
        exprNestLevel++;
        node.accept(this);
        exprNestLevel--;
        return returnExpr;
    }
    // #@@}

    // #@@range/isStatement{
    private boolean isStatement() {
        return (exprNestLevel == 0);
    }

}

