// Generated from Simpiler.g4 by ANTLR 4.6
package simplespy.compiler2017.Parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SimpilerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, Bool=2, Int=3, String=4, Void=5, If=6, For=7, While=8, Break=9, 
		Continue=10, Return=11, New=12, Class=13, This=14, Whitespace=15, Newline=16, 
		BlockComment=17, LineComment=18, BoolConstant=19, IntegerConstant=20, 
		CharacterConstant=21, StringLiteral=22, NullLiteral=23, Plus=24, PlusPlus=25, 
		Minus=26, MinusMinus=27, Star=28, Div=29, Mod=30, Less=31, LessEqual=32, 
		Greater=33, GreaterEqual=34, LeftShift=35, RightShift=36, LParen=37, RParen=38, 
		LBracket=39, RBracket=40, LBrace=41, RBrace=42, And=43, Or=44, AndAnd=45, 
		OrOr=46, Caret=47, Not=48, Tilde=49, Question=50, Colon=51, Semi=52, Comma=53, 
		Assign=54, Equal=55, NotEqual=56, Dot=57, Identifier=58;
	public static final int
		RULE_program = 0, RULE_programSection = 1, RULE_classDeclaration = 2, 
		RULE_functionDefinition = 3, RULE_parameterDeclaration = 4, RULE_variableDeclaration = 5, 
		RULE_classSection = 6, RULE_defaultConstructor = 7, RULE_statement = 8, 
		RULE_block = 9, RULE_blockSection = 10, RULE_expressionStatement = 11, 
		RULE_selectionStatement = 12, RULE_iterationStatement = 13, RULE_jumpStatement = 14, 
		RULE_expression = 15, RULE_type = 16, RULE_nonArrayType = 17, RULE_constant = 18, 
		RULE_creator = 19, RULE_parameterList = 20;
	public static final String[] ruleNames = {
		"program", "programSection", "classDeclaration", "functionDefinition", 
		"parameterDeclaration", "variableDeclaration", "classSection", "defaultConstructor", 
		"statement", "block", "blockSection", "expressionStatement", "selectionStatement", 
		"iterationStatement", "jumpStatement", "expression", "type", "nonArrayType", 
		"constant", "creator", "parameterList"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'else'", "'bool'", "'int'", "'string'", "'void'", "'if'", "'for'", 
		"'while'", "'break'", "'continue'", "'return'", "'new'", "'class'", "'this'", 
		null, null, null, null, null, null, null, null, "'null'", "'+'", "'++'", 
		"'-'", "'--'", "'*'", "'/'", "'%'", "'<'", "'<='", "'>'", "'>='", "'<<'", 
		"'>>'", "'('", "')'", "'['", "']'", "'{'", "'}'", "'&'", "'|'", "'&&'", 
		"'||'", "'^'", "'!'", "'~'", "'?'", "':'", "';'", "','", "'='", "'=='", 
		"'!='", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "Bool", "Int", "String", "Void", "If", "For", "While", "Break", 
		"Continue", "Return", "New", "Class", "This", "Whitespace", "Newline", 
		"BlockComment", "LineComment", "BoolConstant", "IntegerConstant", "CharacterConstant", 
		"StringLiteral", "NullLiteral", "Plus", "PlusPlus", "Minus", "MinusMinus", 
		"Star", "Div", "Mod", "Less", "LessEqual", "Greater", "GreaterEqual", 
		"LeftShift", "RightShift", "LParen", "RParen", "LBracket", "RBracket", 
		"LBrace", "RBrace", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", "Tilde", 
		"Question", "Colon", "Semi", "Comma", "Assign", "Equal", "NotEqual", "Dot", 
		"Identifier"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Simpiler.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SimpilerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SimpilerParser.EOF, 0); }
		public List<ProgramSectionContext> programSection() {
			return getRuleContexts(ProgramSectionContext.class);
		}
		public ProgramSectionContext programSection(int i) {
			return getRuleContext(ProgramSectionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << Class) | (1L << Identifier))) != 0)) {
				{
				{
				setState(42);
				programSection();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramSectionContext extends ParserRuleContext {
		public ProgramSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programSection; }
	 
		public ProgramSectionContext() { }
		public void copyFrom(ProgramSectionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FuncContext extends ProgramSectionContext {
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public FuncContext(ProgramSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitFunc(this);
		}
	}
	public static class VarContext extends ProgramSectionContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public VarContext(ProgramSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitVar(this);
		}
	}
	public static class ClassContext extends ProgramSectionContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public ClassContext(ProgramSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitClass(this);
		}
	}

	public final ProgramSectionContext programSection() throws RecognitionException {
		ProgramSectionContext _localctx = new ProgramSectionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_programSection);
		try {
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				_localctx = new ClassContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				classDeclaration();
				}
				break;
			case 2:
				_localctx = new FuncContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				functionDefinition();
				}
				break;
			case 3:
				_localctx = new VarContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(52);
				variableDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public List<ClassSectionContext> classSection() {
			return getRuleContexts(ClassSectionContext.class);
		}
		public ClassSectionContext classSection(int i) {
			return getRuleContext(ClassSectionContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitClassDeclaration(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(Class);
			setState(56);
			match(Identifier);
			setState(57);
			match(LBrace);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << Identifier))) != 0)) {
				{
				{
				setState(58);
				classSection();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParameterDeclarationContext parameterDeclaration() {
			return getRuleContext(ParameterDeclarationContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitFunctionDefinition(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			type(0);
			setState(67);
			match(Identifier);
			setState(68);
			match(LParen);
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << Identifier))) != 0)) {
				{
				setState(69);
				parameterDeclaration();
				}
			}

			setState(72);
			match(RParen);
			setState(73);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclarationContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(SimpilerParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(SimpilerParser.Identifier, i);
		}
		public ParameterDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterParameterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitParameterDeclaration(this);
		}
	}

	public final ParameterDeclarationContext parameterDeclaration() throws RecognitionException {
		ParameterDeclarationContext _localctx = new ParameterDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parameterDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			type(0);
			setState(76);
			match(Identifier);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(77);
				match(Comma);
				setState(78);
				type(0);
				setState(79);
				match(Identifier);
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitVariableDeclaration(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			type(0);
			setState(87);
			match(Identifier);
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(88);
				match(Assign);
				setState(89);
				expression(0);
				}
			}

			setState(92);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassSectionContext extends ParserRuleContext {
		public ClassSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classSection; }
	 
		public ClassSectionContext() { }
		public void copyFrom(ClassSectionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FuncinClassContext extends ClassSectionContext {
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public FuncinClassContext(ClassSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterFuncinClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitFuncinClass(this);
		}
	}
	public static class ConstructorContext extends ClassSectionContext {
		public DefaultConstructorContext defaultConstructor() {
			return getRuleContext(DefaultConstructorContext.class,0);
		}
		public ConstructorContext(ClassSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitConstructor(this);
		}
	}
	public static class VarinClassContext extends ClassSectionContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public VarinClassContext(ClassSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterVarinClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitVarinClass(this);
		}
	}

	public final ClassSectionContext classSection() throws RecognitionException {
		ClassSectionContext _localctx = new ClassSectionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_classSection);
		try {
			setState(97);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new VarinClassContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(94);
				variableDeclaration();
				}
				break;
			case 2:
				_localctx = new FuncinClassContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				functionDefinition();
				}
				break;
			case 3:
				_localctx = new ConstructorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
				defaultConstructor();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefaultConstructorContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public DefaultConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultConstructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterDefaultConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitDefaultConstructor(this);
		}
	}

	public final DefaultConstructorContext defaultConstructor() throws RecognitionException {
		DefaultConstructorContext _localctx = new DefaultConstructorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_defaultConstructor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(Identifier);
			setState(100);
			match(LParen);
			setState(101);
			match(RParen);
			setState(102);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprstmtContext extends StatementContext {
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public ExprstmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterExprstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitExprstmt(this);
		}
	}
	public static class JumpstmtContext extends StatementContext {
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public JumpstmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterJumpstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitJumpstmt(this);
		}
	}
	public static class IterstmtContext extends StatementContext {
		public IterationStatementContext iterationStatement() {
			return getRuleContext(IterationStatementContext.class,0);
		}
		public IterstmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterIterstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitIterstmt(this);
		}
	}
	public static class BlockstmtContext extends StatementContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockstmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterBlockstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitBlockstmt(this);
		}
	}
	public static class SelectstmtContext extends StatementContext {
		public SelectionStatementContext selectionStatement() {
			return getRuleContext(SelectionStatementContext.class,0);
		}
		public SelectstmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterSelectstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitSelectstmt(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		try {
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBrace:
				_localctx = new BlockstmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				block();
				}
				break;
			case New:
			case This:
			case BoolConstant:
			case IntegerConstant:
			case CharacterConstant:
			case StringLiteral:
			case NullLiteral:
			case Plus:
			case PlusPlus:
			case Minus:
			case MinusMinus:
			case LParen:
			case Not:
			case Tilde:
			case Semi:
			case Identifier:
				_localctx = new ExprstmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				expressionStatement();
				}
				break;
			case If:
				_localctx = new SelectstmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(106);
				selectionStatement();
				}
				break;
			case For:
			case While:
				_localctx = new IterstmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(107);
				iterationStatement();
				}
				break;
			case Break:
			case Continue:
			case Return:
				_localctx = new JumpstmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(108);
				jumpStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<BlockSectionContext> blockSection() {
			return getRuleContexts(BlockSectionContext.class);
		}
		public BlockSectionContext blockSection(int i) {
			return getRuleContext(BlockSectionContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(LBrace);
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << LBrace) | (1L << Not) | (1L << Tilde) | (1L << Semi) | (1L << Identifier))) != 0)) {
				{
				{
				setState(112);
				blockSection();
				}
				}
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(118);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockSectionContext extends ParserRuleContext {
		public BlockSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockSection; }
	 
		public BlockSectionContext() { }
		public void copyFrom(BlockSectionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VardeclContext extends BlockSectionContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public VardeclContext(BlockSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterVardecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitVardecl(this);
		}
	}
	public static class StmtContext extends BlockSectionContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StmtContext(BlockSectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitStmt(this);
		}
	}

	public final BlockSectionContext blockSection() throws RecognitionException {
		BlockSectionContext _localctx = new BlockSectionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_blockSection);
		try {
			setState(122);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new VardeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(120);
				variableDeclaration();
				}
				break;
			case 2:
				_localctx = new StmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitExpressionStatement(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expressionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << Not) | (1L << Tilde) | (1L << Identifier))) != 0)) {
				{
				setState(124);
				expression(0);
				}
			}

			setState(127);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SelectionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterSelectionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitSelectionStatement(this);
		}
	}

	public final SelectionStatementContext selectionStatement() throws RecognitionException {
		SelectionStatementContext _localctx = new SelectionStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_selectionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(If);
			setState(130);
			match(LParen);
			setState(131);
			expression(0);
			setState(132);
			match(RParen);
			setState(133);
			statement();
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(134);
				match(T__0);
				setState(135);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterationStatementContext extends ParserRuleContext {
		public IterationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterationStatement; }
	 
		public IterationStatementContext() { }
		public void copyFrom(IterationStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ForContext extends IterationStatementContext {
		public ExpressionContext init;
		public ExpressionContext cond;
		public ExpressionContext step;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitFor(this);
		}
	}
	public static class WhileContext extends IterationStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitWhile(this);
		}
	}

	public final IterationStatementContext iterationStatement() throws RecognitionException {
		IterationStatementContext _localctx = new IterationStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_iterationStatement);
		int _la;
		try {
			setState(159);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case While:
				_localctx = new WhileContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				match(While);
				setState(139);
				match(LParen);
				setState(140);
				expression(0);
				setState(141);
				match(RParen);
				setState(142);
				statement();
				}
				break;
			case For:
				_localctx = new ForContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				match(For);
				setState(145);
				match(LParen);
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << Not) | (1L << Tilde) | (1L << Identifier))) != 0)) {
					{
					setState(146);
					((ForContext)_localctx).init = expression(0);
					}
				}

				setState(149);
				match(Semi);
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << Not) | (1L << Tilde) | (1L << Identifier))) != 0)) {
					{
					setState(150);
					((ForContext)_localctx).cond = expression(0);
					}
				}

				setState(153);
				match(Semi);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << Not) | (1L << Tilde) | (1L << Identifier))) != 0)) {
					{
					setState(154);
					((ForContext)_localctx).step = expression(0);
					}
				}

				setState(157);
				match(RParen);
				setState(158);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
	 
		public JumpStatementContext() { }
		public void copyFrom(JumpStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BreakContext extends JumpStatementContext {
		public BreakContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitBreak(this);
		}
	}
	public static class ContinueContext extends JumpStatementContext {
		public ContinueContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterContinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitContinue(this);
		}
	}
	public static class ReturnContext extends JumpStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitReturn(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_jumpStatement);
		int _la;
		try {
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Continue:
				_localctx = new ContinueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				match(Continue);
				setState(162);
				match(Semi);
				}
				break;
			case Break:
				_localctx = new BreakContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				match(Break);
				setState(164);
				match(Semi);
				}
				break;
			case Return:
				_localctx = new ReturnContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(165);
				match(Return);
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << Not) | (1L << Tilde) | (1L << Identifier))) != 0)) {
					{
					setState(166);
					expression(0);
					}
				}

				setState(169);
				match(Semi);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PostfixExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PostfixExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterPostfixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitPostfixExpr(this);
		}
	}
	public static class NewContext extends ExpressionContext {
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public NewContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterNew(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitNew(this);
		}
	}
	public static class ThisPointContext extends ExpressionContext {
		public TerminalNode This() { return getToken(SimpilerParser.This, 0); }
		public ThisPointContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterThisPoint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitThisPoint(this);
		}
	}
	public static class MemAccExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public MemAccExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterMemAccExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitMemAccExpr(this);
		}
	}
	public static class IdentifierContext extends ExpressionContext {
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public IdentifierContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitIdentifier(this);
		}
	}
	public static class SubscriptExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SubscriptExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterSubscriptExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitSubscriptExpr(this);
		}
	}
	public static class ConstContext extends ExpressionContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ConstContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitConst(this);
		}
	}
	public static class BinaryExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitBinaryExpr(this);
		}
	}
	public static class UnaryExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitUnaryExpr(this);
		}
	}
	public static class SubExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SubExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterSubExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitSubExpression(this);
		}
	}
	public static class FuncCallExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FuncCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterFuncCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitFuncCallExpr(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PlusPlus:
			case MinusMinus:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(173);
				((UnaryExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PlusPlus || _la==MinusMinus) ) {
					((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(174);
				expression(20);
				}
				break;
			case Plus:
			case Minus:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				((UnaryExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==Plus || _la==Minus) ) {
					((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(176);
				expression(19);
				}
				break;
			case Not:
			case Tilde:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(177);
				((UnaryExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==Not || _la==Tilde) ) {
					((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(178);
				expression(18);
				}
				break;
			case New:
				{
				_localctx = new NewContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(179);
				match(New);
				setState(180);
				creator();
				}
				break;
			case Identifier:
				{
				_localctx = new IdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
				match(Identifier);
				}
				break;
			case BoolConstant:
			case IntegerConstant:
			case CharacterConstant:
			case StringLiteral:
			case NullLiteral:
				{
				_localctx = new ConstContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(182);
				constant();
				}
				break;
			case This:
				{
				_localctx = new ThisPointContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(183);
				match(This);
				}
				break;
			case LParen:
				{
				_localctx = new SubExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(184);
				match(LParen);
				setState(185);
				expression(0);
				setState(186);
				match(RParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(244);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(242);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(190);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(191);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Star) | (1L << Div) | (1L << Mod))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(192);
						expression(17);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(193);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(194);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Plus || _la==Minus) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(195);
						expression(16);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(196);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(197);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LeftShift || _la==RightShift) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(198);
						expression(15);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(199);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(200);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Less || _la==Greater) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(201);
						expression(14);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(202);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(203);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LessEqual || _la==GreaterEqual) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(204);
						expression(13);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(205);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(206);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Equal || _la==NotEqual) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(207);
						expression(12);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(208);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(209);
						((BinaryExprContext)_localctx).op = match(And);
						setState(210);
						expression(11);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(211);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(212);
						((BinaryExprContext)_localctx).op = match(Caret);
						setState(213);
						expression(10);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(214);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(215);
						((BinaryExprContext)_localctx).op = match(Or);
						setState(216);
						expression(9);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(217);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(218);
						((BinaryExprContext)_localctx).op = match(AndAnd);
						setState(219);
						expression(8);
						}
						break;
					case 11:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(220);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(221);
						((BinaryExprContext)_localctx).op = match(OrOr);
						setState(222);
						expression(7);
						}
						break;
					case 12:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(223);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(224);
						((BinaryExprContext)_localctx).op = match(Assign);
						setState(225);
						expression(5);
						}
						break;
					case 13:
						{
						_localctx = new PostfixExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(226);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(227);
						((PostfixExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PlusPlus || _la==MinusMinus) ) {
							((PostfixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 14:
						{
						_localctx = new FuncCallExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(228);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(229);
						match(LParen);
						setState(231);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << This) | (1L << BoolConstant) | (1L << IntegerConstant) | (1L << CharacterConstant) | (1L << StringLiteral) | (1L << NullLiteral) | (1L << Plus) | (1L << PlusPlus) | (1L << Minus) | (1L << MinusMinus) | (1L << LParen) | (1L << Not) | (1L << Tilde) | (1L << Identifier))) != 0)) {
							{
							setState(230);
							parameterList();
							}
						}

						setState(233);
						match(RParen);
						}
						break;
					case 15:
						{
						_localctx = new SubscriptExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(234);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(235);
						match(LBracket);
						setState(236);
						expression(0);
						setState(237);
						match(RBracket);
						}
						break;
					case 16:
						{
						_localctx = new MemAccExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(239);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(240);
						match(Dot);
						setState(241);
						match(Identifier);
						}
						break;
					}
					} 
				}
				setState(246);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArrayTpContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArrayTpContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterArrayTp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitArrayTp(this);
		}
	}
	public static class NonArrayTpContext extends TypeContext {
		public NonArrayTypeContext nonArrayType() {
			return getRuleContext(NonArrayTypeContext.class,0);
		}
		public NonArrayTpContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterNonArrayTp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitNonArrayTp(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new NonArrayTpContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(248);
			nonArrayType();
			}
			_ctx.stop = _input.LT(-1);
			setState(255);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArrayTpContext(new TypeContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(250);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(251);
					match(LBracket);
					setState(252);
					match(RBracket);
					}
					} 
				}
				setState(257);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NonArrayTypeContext extends ParserRuleContext {
		public Token tp;
		public TerminalNode Identifier() { return getToken(SimpilerParser.Identifier, 0); }
		public NonArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonArrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterNonArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitNonArrayType(this);
		}
	}

	public final NonArrayTypeContext nonArrayType() throws RecognitionException {
		NonArrayTypeContext _localctx = new NonArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_nonArrayType);
		try {
			setState(263);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Int:
				enterOuterAlt(_localctx, 1);
				{
				setState(258);
				((NonArrayTypeContext)_localctx).tp = match(Int);
				}
				break;
			case Bool:
				enterOuterAlt(_localctx, 2);
				{
				setState(259);
				((NonArrayTypeContext)_localctx).tp = match(Bool);
				}
				break;
			case String:
				enterOuterAlt(_localctx, 3);
				{
				setState(260);
				((NonArrayTypeContext)_localctx).tp = match(String);
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 4);
				{
				setState(261);
				((NonArrayTypeContext)_localctx).tp = match(Void);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 5);
				{
				setState(262);
				((NonArrayTypeContext)_localctx).tp = match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public Token tp;
		public TerminalNode IntegerConstant() { return getToken(SimpilerParser.IntegerConstant, 0); }
		public TerminalNode CharacterConstant() { return getToken(SimpilerParser.CharacterConstant, 0); }
		public TerminalNode StringLiteral() { return getToken(SimpilerParser.StringLiteral, 0); }
		public TerminalNode NullLiteral() { return getToken(SimpilerParser.NullLiteral, 0); }
		public TerminalNode BoolConstant() { return getToken(SimpilerParser.BoolConstant, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_constant);
		try {
			setState(270);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntegerConstant:
				enterOuterAlt(_localctx, 1);
				{
				setState(265);
				((ConstantContext)_localctx).tp = match(IntegerConstant);
				}
				break;
			case CharacterConstant:
				enterOuterAlt(_localctx, 2);
				{
				setState(266);
				((ConstantContext)_localctx).tp = match(CharacterConstant);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(267);
				((ConstantContext)_localctx).tp = match(StringLiteral);
				}
				break;
			case NullLiteral:
				enterOuterAlt(_localctx, 4);
				{
				setState(268);
				((ConstantContext)_localctx).tp = match(NullLiteral);
				}
				break;
			case BoolConstant:
				enterOuterAlt(_localctx, 5);
				{
				setState(269);
				((ConstantContext)_localctx).tp = match(BoolConstant);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreatorContext extends ParserRuleContext {
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
	 
		public CreatorContext() { }
		public void copyFrom(CreatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CreatorErrorContext extends CreatorContext {
		public NonArrayTypeContext nonArrayType() {
			return getRuleContext(NonArrayTypeContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CreatorErrorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterCreatorError(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitCreatorError(this);
		}
	}
	public static class CreatorNonArrayContext extends CreatorContext {
		public NonArrayTypeContext nonArrayType() {
			return getRuleContext(NonArrayTypeContext.class,0);
		}
		public CreatorNonArrayContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterCreatorNonArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitCreatorNonArray(this);
		}
	}
	public static class CreatorArrayContext extends CreatorContext {
		public NonArrayTypeContext nonArrayType() {
			return getRuleContext(NonArrayTypeContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CreatorArrayContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterCreatorArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitCreatorArray(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_creator);
		try {
			int _alt;
			setState(312);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new CreatorErrorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(272);
				nonArrayType();
				setState(277); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(273);
						match(LBracket);
						setState(274);
						expression(0);
						setState(275);
						match(RBracket);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(279); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(283); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(281);
						match(LBracket);
						setState(282);
						match(RBracket);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(285); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(291); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(287);
						match(LBracket);
						setState(288);
						expression(0);
						setState(289);
						match(RBracket);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(293); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				_localctx = new CreatorArrayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(295);
				nonArrayType();
				setState(300); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(296);
						match(LBracket);
						setState(297);
						expression(0);
						setState(298);
						match(RBracket);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(302); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(308);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(304);
						match(LBracket);
						setState(305);
						match(RBracket);
						}
						} 
					}
					setState(310);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
				}
				}
				break;
			case 3:
				_localctx = new CreatorNonArrayContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				nonArrayType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpilerListener ) ((SimpilerListener)listener).exitParameterList(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			expression(0);
			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(315);
				match(Comma);
				setState(316);
				expression(0);
				}
				}
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 15:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 16:
			return type_sempred((TypeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 16);
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 14);
		case 3:
			return precpred(_ctx, 13);
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 11);
		case 6:
			return precpred(_ctx, 10);
		case 7:
			return precpred(_ctx, 9);
		case 8:
			return precpred(_ctx, 8);
		case 9:
			return precpred(_ctx, 7);
		case 10:
			return precpred(_ctx, 6);
		case 11:
			return precpred(_ctx, 5);
		case 12:
			return precpred(_ctx, 24);
		case 13:
			return precpred(_ctx, 23);
		case 14:
			return precpred(_ctx, 22);
		case 15:
			return precpred(_ctx, 21);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 16:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3<\u0145\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\2\3\2\3\3\3\3\3\3\5\38\n\3\3\4\3\4\3\4\3\4\7\4>\n\4\f\4\16\4A\13\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\5\5I\n\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\7\6T\n\6\f\6\16\6W\13\6\3\7\3\7\3\7\3\7\5\7]\n\7\3\7\3\7\3\b\3\b\3\b"+
		"\5\bd\n\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\5\np\n\n\3\13\3\13\7"+
		"\13t\n\13\f\13\16\13w\13\13\3\13\3\13\3\f\3\f\5\f}\n\f\3\r\5\r\u0080\n"+
		"\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u008b\n\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u0096\n\17\3\17\3\17\5\17\u009a"+
		"\n\17\3\17\3\17\5\17\u009e\n\17\3\17\3\17\5\17\u00a2\n\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\5\20\u00aa\n\20\3\20\5\20\u00ad\n\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\u00bf\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\5\21\u00ea\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21"+
		"\u00f5\n\21\f\21\16\21\u00f8\13\21\3\22\3\22\3\22\3\22\3\22\3\22\7\22"+
		"\u0100\n\22\f\22\16\22\u0103\13\22\3\23\3\23\3\23\3\23\3\23\5\23\u010a"+
		"\n\23\3\24\3\24\3\24\3\24\3\24\5\24\u0111\n\24\3\25\3\25\3\25\3\25\3\25"+
		"\6\25\u0118\n\25\r\25\16\25\u0119\3\25\3\25\6\25\u011e\n\25\r\25\16\25"+
		"\u011f\3\25\3\25\3\25\3\25\6\25\u0126\n\25\r\25\16\25\u0127\3\25\3\25"+
		"\3\25\3\25\3\25\6\25\u012f\n\25\r\25\16\25\u0130\3\25\3\25\7\25\u0135"+
		"\n\25\f\25\16\25\u0138\13\25\3\25\5\25\u013b\n\25\3\26\3\26\3\26\7\26"+
		"\u0140\n\26\f\26\16\26\u0143\13\26\3\26\2\4 \"\27\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*\2\n\4\2\33\33\35\35\4\2\32\32\34\34\3\2\62\63"+
		"\3\2\36 \3\2%&\4\2!!##\4\2\"\"$$\3\29:\u0170\2/\3\2\2\2\4\67\3\2\2\2\6"+
		"9\3\2\2\2\bD\3\2\2\2\nM\3\2\2\2\fX\3\2\2\2\16c\3\2\2\2\20e\3\2\2\2\22"+
		"o\3\2\2\2\24q\3\2\2\2\26|\3\2\2\2\30\177\3\2\2\2\32\u0083\3\2\2\2\34\u00a1"+
		"\3\2\2\2\36\u00ac\3\2\2\2 \u00be\3\2\2\2\"\u00f9\3\2\2\2$\u0109\3\2\2"+
		"\2&\u0110\3\2\2\2(\u013a\3\2\2\2*\u013c\3\2\2\2,.\5\4\3\2-,\3\2\2\2.\61"+
		"\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\7\2\2\3"+
		"\63\3\3\2\2\2\648\5\6\4\2\658\5\b\5\2\668\5\f\7\2\67\64\3\2\2\2\67\65"+
		"\3\2\2\2\67\66\3\2\2\28\5\3\2\2\29:\7\17\2\2:;\7<\2\2;?\7+\2\2<>\5\16"+
		"\b\2=<\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?\3\2\2\2BC\7,"+
		"\2\2C\7\3\2\2\2DE\5\"\22\2EF\7<\2\2FH\7\'\2\2GI\5\n\6\2HG\3\2\2\2HI\3"+
		"\2\2\2IJ\3\2\2\2JK\7(\2\2KL\5\24\13\2L\t\3\2\2\2MN\5\"\22\2NU\7<\2\2O"+
		"P\7\67\2\2PQ\5\"\22\2QR\7<\2\2RT\3\2\2\2SO\3\2\2\2TW\3\2\2\2US\3\2\2\2"+
		"UV\3\2\2\2V\13\3\2\2\2WU\3\2\2\2XY\5\"\22\2Y\\\7<\2\2Z[\78\2\2[]\5 \21"+
		"\2\\Z\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\66\2\2_\r\3\2\2\2`d\5\f\7\2ad\5"+
		"\b\5\2bd\5\20\t\2c`\3\2\2\2ca\3\2\2\2cb\3\2\2\2d\17\3\2\2\2ef\7<\2\2f"+
		"g\7\'\2\2gh\7(\2\2hi\5\24\13\2i\21\3\2\2\2jp\5\24\13\2kp\5\30\r\2lp\5"+
		"\32\16\2mp\5\34\17\2np\5\36\20\2oj\3\2\2\2ok\3\2\2\2ol\3\2\2\2om\3\2\2"+
		"\2on\3\2\2\2p\23\3\2\2\2qu\7+\2\2rt\5\26\f\2sr\3\2\2\2tw\3\2\2\2us\3\2"+
		"\2\2uv\3\2\2\2vx\3\2\2\2wu\3\2\2\2xy\7,\2\2y\25\3\2\2\2z}\5\f\7\2{}\5"+
		"\22\n\2|z\3\2\2\2|{\3\2\2\2}\27\3\2\2\2~\u0080\5 \21\2\177~\3\2\2\2\177"+
		"\u0080\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\7\66\2\2\u0082\31\3\2\2"+
		"\2\u0083\u0084\7\b\2\2\u0084\u0085\7\'\2\2\u0085\u0086\5 \21\2\u0086\u0087"+
		"\7(\2\2\u0087\u008a\5\22\n\2\u0088\u0089\7\3\2\2\u0089\u008b\5\22\n\2"+
		"\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\33\3\2\2\2\u008c\u008d"+
		"\7\n\2\2\u008d\u008e\7\'\2\2\u008e\u008f\5 \21\2\u008f\u0090\7(\2\2\u0090"+
		"\u0091\5\22\n\2\u0091\u00a2\3\2\2\2\u0092\u0093\7\t\2\2\u0093\u0095\7"+
		"\'\2\2\u0094\u0096\5 \21\2\u0095\u0094\3\2\2\2\u0095\u0096\3\2\2\2\u0096"+
		"\u0097\3\2\2\2\u0097\u0099\7\66\2\2\u0098\u009a\5 \21\2\u0099\u0098\3"+
		"\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\7\66\2\2\u009c"+
		"\u009e\5 \21\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\3\2"+
		"\2\2\u009f\u00a0\7(\2\2\u00a0\u00a2\5\22\n\2\u00a1\u008c\3\2\2\2\u00a1"+
		"\u0092\3\2\2\2\u00a2\35\3\2\2\2\u00a3\u00a4\7\f\2\2\u00a4\u00ad\7\66\2"+
		"\2\u00a5\u00a6\7\13\2\2\u00a6\u00ad\7\66\2\2\u00a7\u00a9\7\r\2\2\u00a8"+
		"\u00aa\5 \21\2\u00a9\u00a8\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\3\2"+
		"\2\2\u00ab\u00ad\7\66\2\2\u00ac\u00a3\3\2\2\2\u00ac\u00a5\3\2\2\2\u00ac"+
		"\u00a7\3\2\2\2\u00ad\37\3\2\2\2\u00ae\u00af\b\21\1\2\u00af\u00b0\t\2\2"+
		"\2\u00b0\u00bf\5 \21\26\u00b1\u00b2\t\3\2\2\u00b2\u00bf\5 \21\25\u00b3"+
		"\u00b4\t\4\2\2\u00b4\u00bf\5 \21\24\u00b5\u00b6\7\16\2\2\u00b6\u00bf\5"+
		"(\25\2\u00b7\u00bf\7<\2\2\u00b8\u00bf\5&\24\2\u00b9\u00bf\7\20\2\2\u00ba"+
		"\u00bb\7\'\2\2\u00bb\u00bc\5 \21\2\u00bc\u00bd\7(\2\2\u00bd\u00bf\3\2"+
		"\2\2\u00be\u00ae\3\2\2\2\u00be\u00b1\3\2\2\2\u00be\u00b3\3\2\2\2\u00be"+
		"\u00b5\3\2\2\2\u00be\u00b7\3\2\2\2\u00be\u00b8\3\2\2\2\u00be\u00b9\3\2"+
		"\2\2\u00be\u00ba\3\2\2\2\u00bf\u00f6\3\2\2\2\u00c0\u00c1\f\22\2\2\u00c1"+
		"\u00c2\t\5\2\2\u00c2\u00f5\5 \21\23\u00c3\u00c4\f\21\2\2\u00c4\u00c5\t"+
		"\3\2\2\u00c5\u00f5\5 \21\22\u00c6\u00c7\f\20\2\2\u00c7\u00c8\t\6\2\2\u00c8"+
		"\u00f5\5 \21\21\u00c9\u00ca\f\17\2\2\u00ca\u00cb\t\7\2\2\u00cb\u00f5\5"+
		" \21\20\u00cc\u00cd\f\16\2\2\u00cd\u00ce\t\b\2\2\u00ce\u00f5\5 \21\17"+
		"\u00cf\u00d0\f\r\2\2\u00d0\u00d1\t\t\2\2\u00d1\u00f5\5 \21\16\u00d2\u00d3"+
		"\f\f\2\2\u00d3\u00d4\7-\2\2\u00d4\u00f5\5 \21\r\u00d5\u00d6\f\13\2\2\u00d6"+
		"\u00d7\7\61\2\2\u00d7\u00f5\5 \21\f\u00d8\u00d9\f\n\2\2\u00d9\u00da\7"+
		".\2\2\u00da\u00f5\5 \21\13\u00db\u00dc\f\t\2\2\u00dc\u00dd\7/\2\2\u00dd"+
		"\u00f5\5 \21\n\u00de\u00df\f\b\2\2\u00df\u00e0\7\60\2\2\u00e0\u00f5\5"+
		" \21\t\u00e1\u00e2\f\7\2\2\u00e2\u00e3\78\2\2\u00e3\u00f5\5 \21\7\u00e4"+
		"\u00e5\f\32\2\2\u00e5\u00f5\t\2\2\2\u00e6\u00e7\f\31\2\2\u00e7\u00e9\7"+
		"\'\2\2\u00e8\u00ea\5*\26\2\u00e9\u00e8\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea"+
		"\u00eb\3\2\2\2\u00eb\u00f5\7(\2\2\u00ec\u00ed\f\30\2\2\u00ed\u00ee\7)"+
		"\2\2\u00ee\u00ef\5 \21\2\u00ef\u00f0\7*\2\2\u00f0\u00f5\3\2\2\2\u00f1"+
		"\u00f2\f\27\2\2\u00f2\u00f3\7;\2\2\u00f3\u00f5\7<\2\2\u00f4\u00c0\3\2"+
		"\2\2\u00f4\u00c3\3\2\2\2\u00f4\u00c6\3\2\2\2\u00f4\u00c9\3\2\2\2\u00f4"+
		"\u00cc\3\2\2\2\u00f4\u00cf\3\2\2\2\u00f4\u00d2\3\2\2\2\u00f4\u00d5\3\2"+
		"\2\2\u00f4\u00d8\3\2\2\2\u00f4\u00db\3\2\2\2\u00f4\u00de\3\2\2\2\u00f4"+
		"\u00e1\3\2\2\2\u00f4\u00e4\3\2\2\2\u00f4\u00e6\3\2\2\2\u00f4\u00ec\3\2"+
		"\2\2\u00f4\u00f1\3\2\2\2\u00f5\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6"+
		"\u00f7\3\2\2\2\u00f7!\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fa\b\22\1\2"+
		"\u00fa\u00fb\5$\23\2\u00fb\u0101\3\2\2\2\u00fc\u00fd\f\4\2\2\u00fd\u00fe"+
		"\7)\2\2\u00fe\u0100\7*\2\2\u00ff\u00fc\3\2\2\2\u0100\u0103\3\2\2\2\u0101"+
		"\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102#\3\2\2\2\u0103\u0101\3\2\2\2"+
		"\u0104\u010a\7\5\2\2\u0105\u010a\7\4\2\2\u0106\u010a\7\6\2\2\u0107\u010a"+
		"\7\7\2\2\u0108\u010a\7<\2\2\u0109\u0104\3\2\2\2\u0109\u0105\3\2\2\2\u0109"+
		"\u0106\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u0108\3\2\2\2\u010a%\3\2\2\2"+
		"\u010b\u0111\7\26\2\2\u010c\u0111\7\27\2\2\u010d\u0111\7\30\2\2\u010e"+
		"\u0111\7\31\2\2\u010f\u0111\7\25\2\2\u0110\u010b\3\2\2\2\u0110\u010c\3"+
		"\2\2\2\u0110\u010d\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u010f\3\2\2\2\u0111"+
		"\'\3\2\2\2\u0112\u0117\5$\23\2\u0113\u0114\7)\2\2\u0114\u0115\5 \21\2"+
		"\u0115\u0116\7*\2\2\u0116\u0118\3\2\2\2\u0117\u0113\3\2\2\2\u0118\u0119"+
		"\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011d\3\2\2\2\u011b"+
		"\u011c\7)\2\2\u011c\u011e\7*\2\2\u011d\u011b\3\2\2\2\u011e\u011f\3\2\2"+
		"\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0125\3\2\2\2\u0121\u0122"+
		"\7)\2\2\u0122\u0123\5 \21\2\u0123\u0124\7*\2\2\u0124\u0126\3\2\2\2\u0125"+
		"\u0121\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2"+
		"\2\2\u0128\u013b\3\2\2\2\u0129\u012e\5$\23\2\u012a\u012b\7)\2\2\u012b"+
		"\u012c\5 \21\2\u012c\u012d\7*\2\2\u012d\u012f\3\2\2\2\u012e\u012a\3\2"+
		"\2\2\u012f\u0130\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131"+
		"\u0136\3\2\2\2\u0132\u0133\7)\2\2\u0133\u0135\7*\2\2\u0134\u0132\3\2\2"+
		"\2\u0135\u0138\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u013b"+
		"\3\2\2\2\u0138\u0136\3\2\2\2\u0139\u013b\5$\23\2\u013a\u0112\3\2\2\2\u013a"+
		"\u0129\3\2\2\2\u013a\u0139\3\2\2\2\u013b)\3\2\2\2\u013c\u0141\5 \21\2"+
		"\u013d\u013e\7\67\2\2\u013e\u0140\5 \21\2\u013f\u013d\3\2\2\2\u0140\u0143"+
		"\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142+\3\2\2\2\u0143"+
		"\u0141\3\2\2\2\"/\67?HU\\cou|\177\u008a\u0095\u0099\u009d\u00a1\u00a9"+
		"\u00ac\u00be\u00e9\u00f4\u00f6\u0101\u0109\u0110\u0119\u011f\u0127\u0130"+
		"\u0136\u013a\u0141";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}