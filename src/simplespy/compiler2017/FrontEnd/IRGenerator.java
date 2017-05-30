package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Asm.AsmType;
import simplespy.compiler2017.Asm.Label;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.NodeFamily.IRNode.*;
import simplespy.compiler2017.Utils.ListUtils;

import java.util.*;

/**
 * Created by spy on 5/6/17.
 */
public class IRGenerator implements ASTVisitor {
    private TypeTable typeTable;
    public static IRRoot ir;
    VarDecNode currentVar;

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
        for (ASTBranch member : node.getMembers()){
            if (member instanceof FuncDefNode){
                ((FuncDefNode) member).setIr(compileFunctionBody((FuncDefNode) member));
                node.addFunc((FuncDefNode) member);
            } else if (member instanceof VarDecNode) {
                node.addVar((VarDecNode) member);
            }else if (member instanceof ConstructorNode){
                stmts = new ArrayList<>();
                scopeStack = new Stack<>();
                breakStack = new Stack<>();
                continueStack = new Stack<>();
                ((ConstructorNode)member).body.accept(this);
                ((ConstructorNode)member).setIr(stmts);
                node.constructor = (ConstructorNode)member;
            }
        }
    }

    @Override
    public void visit(FuncDefNode node) {//global functions
        node.setIr(compileFunctionBody(node));
        ir.funcs.put(node.name, node);
    }

    @Override
    public void visit(VarDecNode node) {//global variables
        currentVar = node;
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
    public void visit(ConstructorNode node) {}

    @Override
    public void visit(StmtNode node) {
        node.accept(this);
    }

    @Override
    public void visit(BlockNode node) {
        scopeStack.push(node.scope);
        node.getStmts().stream().forEachOrdered(x->{
            visit(x);
            if (x instanceof VarDecInBlockNode){
                node.addVar(((VarDecInBlockNode) x).getVardec());
            }
        });
        scopeStack.pop();
    }

    @Override
    public void visit(VarDecInBlockNode node) {//local variables
        currentVar = node.getVardec();
        if (node.getVardec().init != null) {
            Expr init = transformExpr(node.getVardec().init);
            assign(node.getLoc(), ref(node.getVardec()), init);
            node.getVardec().setIr(init);
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
        stmts.add(new LabelStmt(node.getLoc(), begLabel));
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
        if (node.getOp().equals(BinaryOpNode.BinaryOp.LOGICAL_AND)) {
            if (left instanceof Int && right instanceof Int){
                Int l = (Int) left;
                Int r = (Int) right;
                returnExpr = calculate(BinaryOpNode.BinaryOp.BITWISE_AND, l, r);
            }
            else {
                Label rightLabel = new Label();
                Label endLabel = new Label();
                VarDecNode var = tmpVar(node.type);
                assign(node.getLoc(), ref(var), left);
                stmts.add(new CJump(node.getLoc(), ref(var), rightLabel, endLabel));
                stmts.add(new LabelStmt(node.getLoc(), rightLabel));
                assign(node.getLoc(), ref(var), right);
                stmts.add(new LabelStmt(node.getLoc(), endLabel));
                returnExpr = isStatement() ? null : ref(var);
            }
        } else if (node.getOp().equals(BinaryOpNode.BinaryOp.LOGICAL_OR)) {
            if (left instanceof Int && right instanceof Int){
                Int l = (Int) left;
                Int r = (Int) right;
                returnExpr = calculate(BinaryOpNode.BinaryOp.BITWISE_OR, l, r);
            }
            else {
                Label rightLabel = new Label();
                Label endLabel = new Label();
                VarDecNode var = tmpVar(node.type);
                assign(node.getLoc(), ref(var), left);
                stmts.add(new CJump(node.getLoc(), ref(var), endLabel, rightLabel));
                stmts.add(new LabelStmt(node.getLoc(), rightLabel));
                assign(node.getLoc(), ref(var), right);
                stmts.add(new LabelStmt(node.getLoc(), endLabel));
                returnExpr = isStatement() ? null : ref(var);
            }
        } else if (node.getOp().equals(BinaryOpNode.BinaryOp.ASSIGN)) {
            assign(node.getLoc(), left, right);
            returnExpr = null;
        } else {
                returnExpr = new Bin(node.getOp(), left, right);

        }
    }

    @Override
    public void visit(UnaryOpNode node) {
        if (node.getOp().equals(UnaryOpNode.UnaryOp.POS)) {
            returnExpr = transformExpr(node.body);
        } else if (node.getOp().equals(UnaryOpNode.UnaryOp.INC)) {
            returnExpr = transformOpAssign(node.getLoc(), node.type, BinaryOpNode.BinaryOp.ADD, transformExpr(node.body), new Int(1));
        } else if (node.getOp().equals(UnaryOpNode.UnaryOp.DEC)) {
            returnExpr = transformOpAssign(node.getLoc(), node.type, BinaryOpNode.BinaryOp.SUB, transformExpr(node.body), new Int(1));
        } else
            returnExpr = new Uni(node.getOp(), transformExpr(node.body));
    }

    @Override
    public void visit(MemberNode node) {
        Node entity = node.getEntity();
        if (entity instanceof FuncDefNode){//member function
            argThis = transformExpr(node.expr);//.addressNode();
            returnExpr =  ref(entity);
        }else if (entity instanceof VarDecNode){//member
            TypeNode type = node.expr.type;
            Expr expr = transformExpr(node.expr).addressNode(); //&expr
            if (type instanceof ClassType) {
                String className = ((ClassType) type).name;
                ClassDefNode classEntity = typeTable.getClassDefNode(className);
                int offset = classEntity.getOffset((VarDecNode) entity);
                Expr addr = new Bin(BinaryOpNode.BinaryOp.ADD, expr, new Int(SIZE * offset));
                returnExpr = node.isLoadable() ? new Mem(addr) : addr;
            }
        } else if (node.expr.type instanceof ArrayType) {
            if (node.member.name.equals("size")) {
                argThis = transformExpr(node.expr);
                returnExpr =  ref(ir.scope.array.get("size"));
            }
        } else {
            throw new Error("membernode");
        }
    }//function of Array and String is to be done

    @Override
    public void visit(SuffixOpNode node) {
        Expr expr = transformExpr(node.expr);
        BinaryOpNode.BinaryOp op = node.getOp().toString().equals("INC") ? BinaryOpNode.BinaryOp.ADD : BinaryOpNode.BinaryOp.SUB;
        if (isStatement()) {
            transformOpAssign(node.getLoc(), node.type, op, expr, new Int(1));
        } else if (expr.isConstant()) {
            returnExpr = calculate(op, expr, new Int(1));
        } else if (expr.isVar()) {
            VarDecNode tmp = tmpVar(node.expr.getType());
            assign(node.getLoc(), ref(tmp), expr);
            assign(node.getLoc(), expr, new Bin(op, ref(tmp), new Int(1)));
            returnExpr = ref(tmp);
        } else {//arefnode
            VarDecNode a = tmpVar(node.expr.type);
            VarDecNode v = tmpVar(node.expr.type);
            assign(node.getLoc(), ref(a), expr.addressNode());
            assign(node.getLoc(), ref(v), mem(a));
            assign(node.getLoc(), mem(a), new Bin(op, mem(a), new Int(1)));
            returnExpr = ref(v);
        }
    }
    @Override
    public void visit(NewNode node) {
        Malloc space = new Malloc();
        space.setEntity(node);
        int size = 1;
        if (node.getType() instanceof ClassType){
            ClassDefNode type = ir.typeTable.getClassDefNode(((ClassType) node.getType()).name);
            size = type.getMemorySize();
        }
        Expr[] base = {new Int(SIZE * size)};
        node.item.stream().filter(x->x != null).forEachOrdered(x-> {
            Expr exprx = transformExpr(x);
            space.arraySize = exprx;
            Expr addx = exprx;
            if(exprx.isConstant()){
                addx = calculate(BinaryOpNode.BinaryOp.ADD, exprx, new Int(1));
            }else addx = new Bin(BinaryOpNode.BinaryOp.ADD, exprx, new Int(1));
            Expr total = new Bin(BinaryOpNode.BinaryOp.MUL, base[0], addx);
            base[0] = total;
        });
        space.spaceSize = base[0];
        returnExpr = space;
    }

    @Override
    public void visit(IDNode node) {
        Var var = ref(node.entity);
        returnExpr = node.isLoadable() ? var : var.addressNode();
    }

    static final int SIZE = 8;

    @Override
    public void visit(ArefNode node) {
        Expr expr = transformExpr(node.getExpr());
        Expr index = transformExpr(node.getIndex());
        Expr offset;
        if (index.isConstant()){
            Expr newindex = calculate(BinaryOpNode.BinaryOp.ADD, index, new Int(1));
            offset = calculate(BinaryOpNode.BinaryOp.MUL, new Int(SIZE), newindex);
        }else {
            Expr newindex = new Bin(BinaryOpNode.BinaryOp.ADD, index, new Int(1));
            offset = new Bin(BinaryOpNode.BinaryOp.MUL, new Int(SIZE), newindex);
        }
        Expr addr;
        if (expr.isConstant() && offset.isConstant()){
            addr = calculate(BinaryOpNode.BinaryOp.ADD, expr, offset);
        }else {
            addr = new Bin(BinaryOpNode.BinaryOp.ADD, expr, offset);
        }
        returnExpr = new Mem(addr);
    }

    @Override
    public void visit(ThisNode node) {
        returnExpr = new Var(node);
        //to be done
    }

    Expr argThis = null;

    @Override
    public void visit(FuncallNode node) {
        List<Expr> args = new ArrayList<>();
        ListUtils.reverse(node.parameters).stream().forEachOrdered(x -> args.add(0, transformExpr(x)));
        Call call = new Call(transformExpr(node.name), args);
        if (argThis != null){
            call.argThis = argThis;
            argThis = null;
        }
        if (isStatement()) {
            stmts.add(new ExprStmt(node.getLoc(), call));
        } else {
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
        returnExpr = new Str(node.value, node);

    }

    @Override
    public void visit(NullLiteralNode node) {returnExpr = new Null();}

    @Override
    public void visit(EmptyNode node) {}

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
    private boolean isStatement() {
        return (exprNestLevel == 0);
    }

    private void assign(Location loc, Expr lhs, Expr rhs) {
        if (lhs instanceof Addr && rhs instanceof Malloc){
            stmts.add(new Assign(loc, lhs, rhs));
        }
        else if (lhs instanceof Addr && rhs instanceof Addr){
            stmts.add(new Assign(loc, lhs, rhs));
        }else if (lhs instanceof Addr && rhs instanceof Null){
            stmts.add(new Assign(loc, lhs, rhs));
        }
        else{
        stmts.add(new Assign(loc, lhs.addressNode(), rhs));}
    }

    private Mem mem(Node ent) {
        return new Mem(ref(ent));
    }

    private Var ref(Node ent) {
        return new Var(ent);
    }

    private Expr transformOpAssign(Location loc, TypeNode type, BinaryOpNode.BinaryOp op,  Expr lhs, Expr rhs) {
        if (lhs.isVar()) {
            // cont(lhs += rhs) -> lhs = lhs + rhs; cont(lhs)
            assign(loc, lhs, new Bin(op, lhs, rhs));
            return isStatement() ? null : lhs;
        }
        else {
            // cont(lhs += rhs) -> a = &lhs; *a = *a + rhs; cont(*a)
            VarDecNode a = tmpVar(type);
            assign(loc, ref(a), lhs.addressNode());
            assign(loc, mem(a), new Bin(op, mem(a), rhs));
            return isStatement() ? null : mem(a);
        }
    }



    private Expr calculate(BinaryOpNode.BinaryOp op, Expr leftexpr, Expr rightexpr){
        if (leftexpr instanceof Int && rightexpr instanceof Int) {
            int left = ((Int) leftexpr).getValue();
            int right = ((Int) rightexpr).getValue();
            switch (op) {
                case ADD:
                    return new Int(left + right);
                case SUB:
                    return new Int(left - right);
                case MUL:
                    return new Int(left * right);
                case DIV:
                    return new Int(left / right);
                case MOD:
                    return new Int(left % right);
                case BITWISE_AND:
                    return new Int(left & right);
                case BITWISE_OR:
                    return new Int(left | right);
                case XOR:
                    return new Int(left ^ right);
                case SHL:
                    return new Int(left << right);
                case SHR:
                    return new Int(left >> right);
                default:
                    switch (op) {
                        case EQ:    return new Int(left == right ? 1 : 0);
                        case NE:    return new Int(left != right ? 1 : 0);
                        case GT:   return new Int(left > right ? 1 : 0);
                        case GE:   return new Int(left >= right ? 1 : 0);
                        case LT:    return new Int(left < right ? 1 : 0);
                        case LE:    return new Int(left <= right ? 1 : 0);
                        default:
                            throw new Error("unknown binary operator: " + op);
                    }

            }
        }else if (leftexpr instanceof Str && rightexpr instanceof Str){
           /* String left = ((Str) leftexpr).getValue();
            String right =  ((Str) rightexpr).getValue();

            if (op.equals(BinaryOpNode.BinaryOp.ADD)) return new Str(left.concat(right), null);*/
        }else{
            throw  new Error("constant calculate");
        }
        return null;
    }

}

