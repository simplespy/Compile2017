// Generated from Simpiler.g4 by ANTLR 4.6
package simplespy.compiler2017.Parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpilerParser}.
 */
public interface SimpilerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SimpilerParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SimpilerParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code class}
	 * labeled alternative in {@link SimpilerParser#programSection}.
	 * @param ctx the parse tree
	 */
	void enterClass(SimpilerParser.ClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code class}
	 * labeled alternative in {@link SimpilerParser#programSection}.
	 * @param ctx the parse tree
	 */
	void exitClass(SimpilerParser.ClassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code func}
	 * labeled alternative in {@link SimpilerParser#programSection}.
	 * @param ctx the parse tree
	 */
	void enterFunc(SimpilerParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code func}
	 * labeled alternative in {@link SimpilerParser#programSection}.
	 * @param ctx the parse tree
	 */
	void exitFunc(SimpilerParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code var}
	 * labeled alternative in {@link SimpilerParser#programSection}.
	 * @param ctx the parse tree
	 */
	void enterVar(SimpilerParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code var}
	 * labeled alternative in {@link SimpilerParser#programSection}.
	 * @param ctx the parse tree
	 */
	void exitVar(SimpilerParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(SimpilerParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(SimpilerParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(SimpilerParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(SimpilerParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(SimpilerParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(SimpilerParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(SimpilerParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(SimpilerParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarinClass}
	 * labeled alternative in {@link SimpilerParser#classSection}.
	 * @param ctx the parse tree
	 */
	void enterVarinClass(SimpilerParser.VarinClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarinClass}
	 * labeled alternative in {@link SimpilerParser#classSection}.
	 * @param ctx the parse tree
	 */
	void exitVarinClass(SimpilerParser.VarinClassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncinClass}
	 * labeled alternative in {@link SimpilerParser#classSection}.
	 * @param ctx the parse tree
	 */
	void enterFuncinClass(SimpilerParser.FuncinClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncinClass}
	 * labeled alternative in {@link SimpilerParser#classSection}.
	 * @param ctx the parse tree
	 */
	void exitFuncinClass(SimpilerParser.FuncinClassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constructor}
	 * labeled alternative in {@link SimpilerParser#classSection}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(SimpilerParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constructor}
	 * labeled alternative in {@link SimpilerParser#classSection}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(SimpilerParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#defaultConstructor}.
	 * @param ctx the parse tree
	 */
	void enterDefaultConstructor(SimpilerParser.DefaultConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#defaultConstructor}.
	 * @param ctx the parse tree
	 */
	void exitDefaultConstructor(SimpilerParser.DefaultConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockstmt(SimpilerParser.BlockstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockstmt(SimpilerParser.BlockstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprstmt(SimpilerParser.ExprstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprstmt(SimpilerParser.ExprstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSelectstmt(SimpilerParser.SelectstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSelectstmt(SimpilerParser.SelectstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code iterstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIterstmt(SimpilerParser.IterstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code iterstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIterstmt(SimpilerParser.IterstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterJumpstmt(SimpilerParser.JumpstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpstmt}
	 * labeled alternative in {@link SimpilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitJumpstmt(SimpilerParser.JumpstmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SimpilerParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SimpilerParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code vardecl}
	 * labeled alternative in {@link SimpilerParser#blockSection}.
	 * @param ctx the parse tree
	 */
	void enterVardecl(SimpilerParser.VardeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code vardecl}
	 * labeled alternative in {@link SimpilerParser#blockSection}.
	 * @param ctx the parse tree
	 */
	void exitVardecl(SimpilerParser.VardeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link SimpilerParser#blockSection}.
	 * @param ctx the parse tree
	 */
	void enterStmt(SimpilerParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link SimpilerParser#blockSection}.
	 * @param ctx the parse tree
	 */
	void exitStmt(SimpilerParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(SimpilerParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(SimpilerParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(SimpilerParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(SimpilerParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code while}
	 * labeled alternative in {@link SimpilerParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhile(SimpilerParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code while}
	 * labeled alternative in {@link SimpilerParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhile(SimpilerParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code for}
	 * labeled alternative in {@link SimpilerParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterFor(SimpilerParser.ForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code for}
	 * labeled alternative in {@link SimpilerParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitFor(SimpilerParser.ForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continue}
	 * labeled alternative in {@link SimpilerParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinue(SimpilerParser.ContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continue}
	 * labeled alternative in {@link SimpilerParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinue(SimpilerParser.ContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code break}
	 * labeled alternative in {@link SimpilerParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreak(SimpilerParser.BreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code break}
	 * labeled alternative in {@link SimpilerParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreak(SimpilerParser.BreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code return}
	 * labeled alternative in {@link SimpilerParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturn(SimpilerParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code return}
	 * labeled alternative in {@link SimpilerParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturn(SimpilerParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PostfixExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(SimpilerParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PostfixExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(SimpilerParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code New}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNew(SimpilerParser.NewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code New}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNew(SimpilerParser.NewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisPoint}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisPoint(SimpilerParser.ThisPointContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisPoint}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisPoint(SimpilerParser.ThisPointContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MemAccExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemAccExpr(SimpilerParser.MemAccExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MemAccExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemAccExpr(SimpilerParser.MemAccExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SimpilerParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SimpilerParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubscriptExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExpr(SimpilerParser.SubscriptExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubscriptExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExpr(SimpilerParser.SubscriptExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Const}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConst(SimpilerParser.ConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Const}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConst(SimpilerParser.ConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(SimpilerParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(SimpilerParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(SimpilerParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(SimpilerParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubExpression}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(SimpilerParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubExpression}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(SimpilerParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(SimpilerParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link SimpilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(SimpilerParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayTp}
	 * labeled alternative in {@link SimpilerParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayTp(SimpilerParser.ArrayTpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayTp}
	 * labeled alternative in {@link SimpilerParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayTp(SimpilerParser.ArrayTpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonArrayTp}
	 * labeled alternative in {@link SimpilerParser#type}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayTp(SimpilerParser.NonArrayTpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonArrayTp}
	 * labeled alternative in {@link SimpilerParser#type}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayTp(SimpilerParser.NonArrayTpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#nonArrayType}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayType(SimpilerParser.NonArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#nonArrayType}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayType(SimpilerParser.NonArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(SimpilerParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(SimpilerParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code creatorError}
	 * labeled alternative in {@link SimpilerParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreatorError(SimpilerParser.CreatorErrorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code creatorError}
	 * labeled alternative in {@link SimpilerParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreatorError(SimpilerParser.CreatorErrorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code creatorArray}
	 * labeled alternative in {@link SimpilerParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreatorArray(SimpilerParser.CreatorArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code creatorArray}
	 * labeled alternative in {@link SimpilerParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreatorArray(SimpilerParser.CreatorArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code creatorNonArray}
	 * labeled alternative in {@link SimpilerParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreatorNonArray(SimpilerParser.CreatorNonArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code creatorNonArray}
	 * labeled alternative in {@link SimpilerParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreatorNonArray(SimpilerParser.CreatorNonArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpilerParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(SimpilerParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpilerParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(SimpilerParser.ParameterListContext ctx);
}