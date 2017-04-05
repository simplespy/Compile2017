package simplespy.compiler2017.FrontEnd;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.Parser.SimpilerBaseListener;
import simplespy.compiler2017.Parser.SimpilerParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Created by spy on 17/3/25.
 */
public class ASTBuilder extends SimpilerBaseListener {
    private ParseTreeProperty<Object> nodeMap = new ParseTreeProperty<>();
    public static ASTRoot ast;
    public ASTRoot getAst(){ return ast;}

    public ASTBuilder(){
        ast = new ASTRoot();
    }

    public Location getLocation(ParserRuleContext ctx){
        return new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    }


    @Override
    public void exitProgram(SimpilerParser.ProgramContext ctx) {
        ctx.programSection().stream().map(nodeMap::get).forEachOrdered(ast::add);
    }

    @Override
    public void exitBinaryExpr(SimpilerParser.BinaryExprContext ctx){
        BinaryOpNode.BinaryOp op;
        ExprNode left = (ExprNode) nodeMap.get(ctx.expression(0));
        ExprNode right = (ExprNode) nodeMap.get(ctx.expression(1));
        switch (ctx.op.getType()){
            case SimpilerParser.Star: op = BinaryOpNode.BinaryOp.MUL; break;
            case SimpilerParser.Div: op = BinaryOpNode.BinaryOp.DIV; break;
            case SimpilerParser.Mod: op = BinaryOpNode.BinaryOp.MOD; break;
            case SimpilerParser.Plus: op = BinaryOpNode.BinaryOp.ADD; break;
            case SimpilerParser.Minus: op = BinaryOpNode.BinaryOp.SUB; break;
            case SimpilerParser.LeftShift: op = BinaryOpNode.BinaryOp.SHL; break;
            case SimpilerParser.RightShift: op = BinaryOpNode.BinaryOp.SHR; break;
            case SimpilerParser.Less: op = BinaryOpNode.BinaryOp.LT; break;
            case SimpilerParser.Greater: op = BinaryOpNode.BinaryOp.GT; break;
            case SimpilerParser.LessEqual: op = BinaryOpNode.BinaryOp.LE; break;
            case SimpilerParser.GreaterEqual: op = BinaryOpNode.BinaryOp.GE; break;
            case SimpilerParser.Equal: op = BinaryOpNode.BinaryOp.EQ; break;
            case SimpilerParser.NotEqual: op = BinaryOpNode.BinaryOp.NE; break;
            case SimpilerParser.And: op = BinaryOpNode.BinaryOp.BITWISE_AND; break;
            case SimpilerParser.Caret: op = BinaryOpNode.BinaryOp.XOR; break;
            case SimpilerParser.Or: op = BinaryOpNode.BinaryOp.BITWISE_OR; break;
            case SimpilerParser.AndAnd: op = BinaryOpNode.BinaryOp.LOGICAL_AND; break;
            case SimpilerParser.OrOr: op = BinaryOpNode.BinaryOp.LOGICAL_OR; break;
            case SimpilerParser.Assign: op = BinaryOpNode.BinaryOp.ASSIGN; break;
            default: throw new RuntimeException("Unknown Binary Operator.");
        }

        nodeMap.put(ctx, new BinaryOpNode(left, op, right, getLocation(ctx)));

    }

    @Override
    public void exitBlock(SimpilerParser.BlockContext ctx) {
        BlockNode block = new BlockNode(getLocation(ctx));
        ctx.blockSection().stream().map(nodeMap::get).forEachOrdered(block::add);
        nodeMap.put(ctx, block);
    }
//blockSection
    @Override
    public void exitStmt(SimpilerParser.StmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.statement()));
    }

    @Override
    public void exitVardecl(SimpilerParser.VardeclContext ctx) {
        nodeMap.put(ctx, new VarDecInBlockNode((VarDecNode)nodeMap.get(ctx.variableDeclaration()), getLocation(ctx)));
    }

    @Override
    public void exitBreak(SimpilerParser.BreakContext ctx) {
        nodeMap.put(ctx, new BreakNode(getLocation(ctx)));
    }

    @Override
    public void exitClassDeclaration(SimpilerParser.ClassDeclarationContext ctx) {
        String name = ctx.Identifier().getText();
        ClassDefNode classDefNode = new ClassDefNode(name, getLocation(ctx));

        ClassType classType = new ClassType(name, getLocation(ctx));
        ast.typeTable.put(name, classType, new LocalScope(ast.globalScope));

        ctx.classSection().stream().map(nodeMap::get).forEachOrdered(classDefNode::add);
        nodeMap.put(ctx, classDefNode);

    }

    @Override
    public void exitDefaultConstructor(SimpilerParser.DefaultConstructorContext ctx) {
        String name = ctx.Identifier().getText();
        BlockNode body = (BlockNode) nodeMap.get(ctx.block());
        nodeMap.put(ctx, new ConstructorNode(name, body, getLocation(ctx)));    }

    @Override
    public void exitConstructor(SimpilerParser.ConstructorContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.defaultConstructor()));
    }

    @Override
    public void exitFuncinClass(SimpilerParser.FuncinClassContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.functionDefinition()));
    }


    @Override
    public void exitVarinClass(SimpilerParser.VarinClassContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.variableDeclaration()));
    }

    @Override
    public void exitConst(SimpilerParser.ConstContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.constant()));
    }

    @Override
    public void exitConstant(SimpilerParser.ConstantContext ctx) {
        String s = ctx.tp.getText();
        int type = ctx.tp.getType();
        switch (type){
            case SimpilerParser.IntegerConstant: nodeMap.put(ctx, new IntLiteralNode(Integer.valueOf(s))); break;
            case SimpilerParser.NullLiteral: nodeMap.put(ctx, new NullLiteralNode()); break;
            case SimpilerParser.BoolConstant: nodeMap.put(ctx, new BoolLiteralNode(s.equals("true"), getLocation(ctx))); break;
            case SimpilerParser.StringLiteral: nodeMap.put(ctx, new StringLiteralNode(s)); break;
            default: throw new RuntimeException("Unknown literal");

        }
    }

    @Override
    public void exitContinue(SimpilerParser.ContinueContext ctx) {
        nodeMap.put(ctx, new ContinueNode(getLocation(ctx)));
    }

    @Override
    public void exitCreatorArray(SimpilerParser.CreatorArrayContext ctx) {
        TypeNode type = (TypeNode)nodeMap.get(ctx.nonArrayType());
        NewNode newNode = new NewNode(type);
        ctx.expression().stream().map(nodeMap::get).forEachOrdered(newNode::add);
        int emp = ctx.getTokens(SimpilerParser.LBracket).size() - ctx.expression().size();

        for (int i = 0; i < emp; ++i) {
            newNode.add(null);
        }
        nodeMap.put(ctx, newNode);
    }

    @Override
    public void exitCreatorNonArray(SimpilerParser.CreatorNonArrayContext ctx) {
        nodeMap.put(ctx, new NewNode((TypeNode)nodeMap.get(ctx.nonArrayType())));
    }

    @Override
    public void exitExpressionStatement(SimpilerParser.ExpressionStatementContext ctx) {
        nodeMap.put(ctx, ctx.expression() != null ? nodeMap.get(ctx.expression()) : new EmptyNode(getLocation(ctx)));
    }

    @Override
    public void exitFor(SimpilerParser.ForContext ctx) {
        ExprNode init = ctx.init != null ? (ExprNode)nodeMap.get(ctx.init) : null;
        ExprNode cond = (ExprNode)nodeMap.get(ctx.cond);
        ExprNode step = (ExprNode)nodeMap.get(ctx.step);
        StmtNode body = (StmtNode)nodeMap.get(ctx.statement());
        nodeMap.put(ctx, new ForNode(init, cond, step, body, getLocation(ctx)));
    }

    @Override
    public void exitFuncCallExpr(SimpilerParser.FuncCallExprContext ctx) {
        ExprNode expr = (ExprNode) nodeMap.get(ctx.expression());
        List<ExprNode> paras = (List<ExprNode>) nodeMap.get(ctx.parameterList());
        if (paras == null) paras = new ArrayList<>();
        nodeMap.put(ctx, new FuncallNode(expr, paras, getLocation(ctx)));

    }

    @Override
    public void exitFunctionDefinition(SimpilerParser.FunctionDefinitionContext ctx) {
        String name = ctx.Identifier().getText();
        TypeNode type = (TypeNode) nodeMap.get(ctx.type());
        List<VarDecNode> paras = (List<VarDecNode>) nodeMap.get(ctx.parameterDeclaration());
        BlockNode body = (BlockNode) nodeMap.get(ctx.block());
        if (paras == null) paras = new ArrayList<>();
        nodeMap.put(ctx, new FuncDefNode(type, name, paras, body, getLocation(ctx)));
    }

    @Override
    public void exitIdentifier(SimpilerParser.IdentifierContext ctx) {
        nodeMap.put(ctx, new IDNode(ctx.Identifier().getText(), getLocation(ctx)));
    }

    @Override
    public void exitMemAccExpr(SimpilerParser.MemAccExprContext ctx) {
        nodeMap.put(ctx, new MemberNode((ExprNode)nodeMap.get(ctx.expression()), new IDNode(ctx.Identifier().getText(), getLocation(ctx))));
    }

    @Override
    public void exitNew(SimpilerParser.NewContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.creator()));
    }



    @Override
    public void exitNonArrayType(SimpilerParser.NonArrayTypeContext ctx) {
        switch (ctx.tp.getType()) {
            case SimpilerParser.Int: nodeMap.put(ctx, new BaseType(TypeNode.TYPENAME.INT, getLocation(ctx))); break;
            case SimpilerParser.Bool: nodeMap.put(ctx, new BaseType(TypeNode.TYPENAME.BOOL, getLocation(ctx))); break;
            case SimpilerParser.String: nodeMap.put(ctx, new BaseType(TypeNode.TYPENAME.STRING, getLocation(ctx))); break;
            case SimpilerParser.Void: nodeMap.put(ctx, new BaseType(TypeNode.TYPENAME.VOID, getLocation(ctx))); break;
            case SimpilerParser.Identifier: nodeMap.put(ctx, new ClassType(ctx.Identifier().getText(), getLocation(ctx))); break;
            default: throw new RuntimeException("Unhandled type in nonArrayTypeSpecifier");
        }

    }

    @Override
    public void exitNonArrayTp(SimpilerParser.NonArrayTpContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.nonArrayType()));

    }

    @Override
    public void exitArrayTp(SimpilerParser.ArrayTpContext ctx) {
        nodeMap.put(ctx, new ArrayType((TypeNode) nodeMap.get(ctx.type()), null, getLocation(ctx)));
    }

    @Override
    public void exitParameterDeclaration(SimpilerParser.ParameterDeclarationContext ctx) {
        List<VarDecNode> paras = new ArrayList<>();
        IntStream.range(0, ctx.type().size()).forEachOrdered(i -> paras.add(new VarDecNode(
                    (TypeNode)nodeMap.get(ctx.type(i)),
                    ctx.Identifier(i).getText(),
                    null, getLocation(ctx))));


        nodeMap.put(ctx, paras);
    }

    @Override
    public void exitParameterList(SimpilerParser.ParameterListContext ctx) {
        List<ExprNode> paras = new ArrayList<>();
        ctx.expression().stream().map(nodeMap::get).forEachOrdered(x->paras.add((ExprNode)x));
        nodeMap.put(ctx, paras);
    }

    @Override
    public void exitPostfixExpr(SimpilerParser.PostfixExprContext ctx) {
        ExprNode expr = (ExprNode) nodeMap.get(ctx.expression());
        if (ctx.op.getType() == SimpilerParser.PlusPlus){
            nodeMap.put(ctx, new SuffixOpNode(UnaryOpNode.UnaryOp.INC, expr, getLocation(ctx)));
        }else{
            nodeMap.put(ctx, new SuffixOpNode(UnaryOpNode.UnaryOp.DEC, expr, getLocation(ctx)));
        }
    }


    @Override
    public void exitReturn(SimpilerParser.ReturnContext ctx) {
        nodeMap.put(ctx, new ReturnNode(ctx.expression() != null ?
                                        (ExprNode)nodeMap.get(ctx.expression()) : null,
                                        getLocation(ctx)));
    }
//statements

    @Override
    public void exitSelectionStatement(SimpilerParser.SelectionStatementContext ctx) {
        ExprNode cond = (ExprNode) nodeMap.get(ctx.expression());
        StmtNode then = (StmtNode) nodeMap.get(ctx.statement(0));
        StmtNode otherwise = (StmtNode) nodeMap.get(ctx.statement(1));
        nodeMap.put(ctx, new IfNode(cond, then, otherwise, getLocation(ctx)));
    }
    @Override//return a list
    public void enterBlockstmt(SimpilerParser.BlockstmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.block()));
    }
    @Override
    public void exitExprstmt(SimpilerParser.ExprstmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.expressionStatement()));
    }

    @Override
    public void exitIterstmt(SimpilerParser.IterstmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.iterationStatement()));
    }

    @Override
    public void exitJumpstmt(SimpilerParser.JumpstmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.jumpStatement()));
    }

    @Override
    public void exitSelectstmt(SimpilerParser.SelectstmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.selectionStatement()));
    }

    @Override
    public void exitSubExpression(SimpilerParser.SubExpressionContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.expression()));
    }

    @Override
    public void exitSubscriptExpr(SimpilerParser.SubscriptExprContext ctx) {
        ExprNode array = (ExprNode) nodeMap.get(ctx.expression(0));
        ExprNode index = (ExprNode) nodeMap.get(ctx.expression(1));
        nodeMap.put(ctx, new ArefNode(array, index, getLocation(ctx)));
    }

    @Override
    public void exitThisPoint(SimpilerParser.ThisPointContext ctx) {
        nodeMap.put(ctx, new ThisNode());
    }

    @Override
    public void exitUnaryExpr(SimpilerParser.UnaryExprContext ctx) {
        UnaryOpNode.UnaryOp op;
        switch (ctx.op.getType()){
            case SimpilerParser.PlusPlus: op = UnaryOpNode.UnaryOp.INC; break;
            case SimpilerParser.MinusMinus: op = UnaryOpNode.UnaryOp.DEC; break;
            case SimpilerParser.Plus: op = UnaryOpNode.UnaryOp.POS; break;
            case SimpilerParser.Minus: op = UnaryOpNode.UnaryOp.NEG; break;
            case SimpilerParser.Not: op = UnaryOpNode.UnaryOp.LOGICAL_NOT; break;
            case SimpilerParser.Tilde: op = UnaryOpNode.UnaryOp.BITWISE_NOT; break;
            default: throw new RuntimeException("unknown UnaryOperator");
        }
        nodeMap.put(ctx, new UnaryOpNode(op, (ExprNode) nodeMap.get(ctx.expression())));
    }

    @Override
    public void exitVariableDeclaration(SimpilerParser.VariableDeclarationContext ctx) {

        String name = ctx.Identifier().getText();
        TypeNode type = (TypeNode) nodeMap.get(ctx.type());
        ExprNode init = (ExprNode) nodeMap.get(ctx.expression());
        nodeMap.put(ctx, new VarDecNode(type, name, init, getLocation(ctx)));
    }

    @Override
    public void exitWhile(SimpilerParser.WhileContext ctx) {
        nodeMap.put(ctx, new WhileNode((ExprNode)nodeMap.get(ctx.expression()), (StmtNode)nodeMap.get(ctx.statement()), getLocation(ctx)));
    }

    @Override
    public void exitVar(SimpilerParser.VarContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.variableDeclaration()));
    }

    @Override
    public void exitClass(SimpilerParser.ClassContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.classDeclaration()));
    }



    @Override
    public void exitBlockstmt(SimpilerParser.BlockstmtContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.block()));
    }

    @Override
    public void exitFunc(SimpilerParser.FuncContext ctx) {
        nodeMap.put(ctx, nodeMap.get(ctx.functionDefinition()));

    }

}


