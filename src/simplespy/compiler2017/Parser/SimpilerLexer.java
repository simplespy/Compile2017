// Generated from Simpiler.g4 by ANTLR 4.6
package simplespy.compiler2017.Parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SimpilerLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "Bool", "Int", "String", "Void", "Null", "True", "False", "If", 
		"For", "While", "Break", "Continue", "Return", "New", "Class", "This", 
		"Whitespace", "Newline", "BlockComment", "LineComment", "BoolConstant", 
		"IntegerConstant", "NonzeroDigit", "CharacterConstant", "CCharSequence", 
		"CChar", "StringLiteral", "SCharSequence", "SChar", "EscapeSequence", 
		"SimpleEscapeSequence", "NullLiteral", "Plus", "PlusPlus", "Minus", "MinusMinus", 
		"Star", "Div", "Mod", "Less", "LessEqual", "Greater", "GreaterEqual", 
		"LeftShift", "RightShift", "LParen", "RParen", "LBracket", "RBracket", 
		"LBrace", "RBrace", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", "Tilde", 
		"Question", "Colon", "Semi", "Comma", "Assign", "Equal", "NotEqual", "Dot", 
		"Identifier", "IdentifierNondigit", "Digit"
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


	public SimpilerLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Simpiler.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2<\u01ab\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\3\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\6\23\u00ed\n\23\r\23"+
		"\16\23\u00ee\3\23\3\23\3\24\3\24\5\24\u00f5\n\24\3\24\5\24\u00f8\n\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u0100\n\25\f\25\16\25\u0103\13\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\7\26\u010e\n\26\f\26\16"+
		"\26\u0111\13\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\5\27\u011e\n\27\3\30\3\30\7\30\u0122\n\30\f\30\16\30\u0125\13\30\3\30"+
		"\5\30\u0128\n\30\3\31\3\31\3\32\3\32\3\32\3\32\3\33\6\33\u0131\n\33\r"+
		"\33\16\33\u0132\3\34\3\34\5\34\u0137\n\34\3\35\3\35\5\35\u013b\n\35\3"+
		"\35\3\35\3\36\6\36\u0140\n\36\r\36\16\36\u0141\3\37\3\37\5\37\u0146\n"+
		"\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3$\3$\3$\3%\3%\3&\3&\3&\3"+
		"\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3+\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60"+
		"\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67"+
		"\3\67\38\38\38\39\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3A\3"+
		"A\3B\3B\3B\3C\3C\3C\3D\3D\3E\3E\3E\7E\u01a3\nE\fE\16E\u01a6\13E\3F\3F"+
		"\3G\3G\3\u0101\2H\3\3\5\4\7\5\t\6\13\7\r\2\17\2\21\2\23\b\25\t\27\n\31"+
		"\13\33\f\35\r\37\16!\17#\20%\21\'\22)\23+\24-\25/\26\61\2\63\27\65\2\67"+
		"\29\30;\2=\2?\2A\2C\31E\32G\33I\34K\35M\36O\37Q S!U\"W#Y$[%]&_\'a(c)e"+
		"*g+i,k-m.o/q\60s\61u\62w\63y\64{\65}\66\177\67\u00818\u00839\u0085:\u0087"+
		";\u0089<\u008b\2\u008d\2\3\2\n\4\2\13\13\"\"\4\2\f\f\17\17\3\2\63;\6\2"+
		"\f\f\17\17))^^\6\2\f\f\17\17$$^^\f\2$$))AA^^cdhhppttvvxx\5\2C\\aac|\3"+
		"\2\62;\u01ad\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\63\3\2\2\2\29\3\2\2\2"+
		"\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O"+
		"\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2"+
		"\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2"+
		"\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u"+
		"\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2"+
		"\2\3\u008f\3\2\2\2\5\u0094\3\2\2\2\7\u0099\3\2\2\2\t\u009d\3\2\2\2\13"+
		"\u00a4\3\2\2\2\r\u00a9\3\2\2\2\17\u00ae\3\2\2\2\21\u00b3\3\2\2\2\23\u00b9"+
		"\3\2\2\2\25\u00bc\3\2\2\2\27\u00c0\3\2\2\2\31\u00c6\3\2\2\2\33\u00cc\3"+
		"\2\2\2\35\u00d5\3\2\2\2\37\u00dc\3\2\2\2!\u00e0\3\2\2\2#\u00e6\3\2\2\2"+
		"%\u00ec\3\2\2\2\'\u00f7\3\2\2\2)\u00fb\3\2\2\2+\u0109\3\2\2\2-\u011d\3"+
		"\2\2\2/\u0127\3\2\2\2\61\u0129\3\2\2\2\63\u012b\3\2\2\2\65\u0130\3\2\2"+
		"\2\67\u0136\3\2\2\29\u0138\3\2\2\2;\u013f\3\2\2\2=\u0145\3\2\2\2?\u0147"+
		"\3\2\2\2A\u0149\3\2\2\2C\u014c\3\2\2\2E\u0151\3\2\2\2G\u0153\3\2\2\2I"+
		"\u0156\3\2\2\2K\u0158\3\2\2\2M\u015b\3\2\2\2O\u015d\3\2\2\2Q\u015f\3\2"+
		"\2\2S\u0161\3\2\2\2U\u0163\3\2\2\2W\u0166\3\2\2\2Y\u0168\3\2\2\2[\u016b"+
		"\3\2\2\2]\u016e\3\2\2\2_\u0171\3\2\2\2a\u0173\3\2\2\2c\u0175\3\2\2\2e"+
		"\u0177\3\2\2\2g\u0179\3\2\2\2i\u017b\3\2\2\2k\u017d\3\2\2\2m\u017f\3\2"+
		"\2\2o\u0181\3\2\2\2q\u0184\3\2\2\2s\u0187\3\2\2\2u\u0189\3\2\2\2w\u018b"+
		"\3\2\2\2y\u018d\3\2\2\2{\u018f\3\2\2\2}\u0191\3\2\2\2\177\u0193\3\2\2"+
		"\2\u0081\u0195\3\2\2\2\u0083\u0197\3\2\2\2\u0085\u019a\3\2\2\2\u0087\u019d"+
		"\3\2\2\2\u0089\u019f\3\2\2\2\u008b\u01a7\3\2\2\2\u008d\u01a9\3\2\2\2\u008f"+
		"\u0090\7g\2\2\u0090\u0091\7n\2\2\u0091\u0092\7u\2\2\u0092\u0093\7g\2\2"+
		"\u0093\4\3\2\2\2\u0094\u0095\7d\2\2\u0095\u0096\7q\2\2\u0096\u0097\7q"+
		"\2\2\u0097\u0098\7n\2\2\u0098\6\3\2\2\2\u0099\u009a\7k\2\2\u009a\u009b"+
		"\7p\2\2\u009b\u009c\7v\2\2\u009c\b\3\2\2\2\u009d\u009e\7u\2\2\u009e\u009f"+
		"\7v\2\2\u009f\u00a0\7t\2\2\u00a0\u00a1\7k\2\2\u00a1\u00a2\7p\2\2\u00a2"+
		"\u00a3\7i\2\2\u00a3\n\3\2\2\2\u00a4\u00a5\7x\2\2\u00a5\u00a6\7q\2\2\u00a6"+
		"\u00a7\7k\2\2\u00a7\u00a8\7f\2\2\u00a8\f\3\2\2\2\u00a9\u00aa\7p\2\2\u00aa"+
		"\u00ab\7w\2\2\u00ab\u00ac\7n\2\2\u00ac\u00ad\7n\2\2\u00ad\16\3\2\2\2\u00ae"+
		"\u00af\7v\2\2\u00af\u00b0\7t\2\2\u00b0\u00b1\7w\2\2\u00b1\u00b2\7g\2\2"+
		"\u00b2\20\3\2\2\2\u00b3\u00b4\7h\2\2\u00b4\u00b5\7c\2\2\u00b5\u00b6\7"+
		"n\2\2\u00b6\u00b7\7u\2\2\u00b7\u00b8\7g\2\2\u00b8\22\3\2\2\2\u00b9\u00ba"+
		"\7k\2\2\u00ba\u00bb\7h\2\2\u00bb\24\3\2\2\2\u00bc\u00bd\7h\2\2\u00bd\u00be"+
		"\7q\2\2\u00be\u00bf\7t\2\2\u00bf\26\3\2\2\2\u00c0\u00c1\7y\2\2\u00c1\u00c2"+
		"\7j\2\2\u00c2\u00c3\7k\2\2\u00c3\u00c4\7n\2\2\u00c4\u00c5\7g\2\2\u00c5"+
		"\30\3\2\2\2\u00c6\u00c7\7d\2\2\u00c7\u00c8\7t\2\2\u00c8\u00c9\7g\2\2\u00c9"+
		"\u00ca\7c\2\2\u00ca\u00cb\7m\2\2\u00cb\32\3\2\2\2\u00cc\u00cd\7e\2\2\u00cd"+
		"\u00ce\7q\2\2\u00ce\u00cf\7p\2\2\u00cf\u00d0\7v\2\2\u00d0\u00d1\7k\2\2"+
		"\u00d1\u00d2\7p\2\2\u00d2\u00d3\7w\2\2\u00d3\u00d4\7g\2\2\u00d4\34\3\2"+
		"\2\2\u00d5\u00d6\7t\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8\7v\2\2\u00d8\u00d9"+
		"\7w\2\2\u00d9\u00da\7t\2\2\u00da\u00db\7p\2\2\u00db\36\3\2\2\2\u00dc\u00dd"+
		"\7p\2\2\u00dd\u00de\7g\2\2\u00de\u00df\7y\2\2\u00df \3\2\2\2\u00e0\u00e1"+
		"\7e\2\2\u00e1\u00e2\7n\2\2\u00e2\u00e3\7c\2\2\u00e3\u00e4\7u\2\2\u00e4"+
		"\u00e5\7u\2\2\u00e5\"\3\2\2\2\u00e6\u00e7\7v\2\2\u00e7\u00e8\7j\2\2\u00e8"+
		"\u00e9\7k\2\2\u00e9\u00ea\7u\2\2\u00ea$\3\2\2\2\u00eb\u00ed\t\2\2\2\u00ec"+
		"\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2"+
		"\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\b\23\2\2\u00f1&\3\2\2\2\u00f2\u00f4"+
		"\7\17\2\2\u00f3\u00f5\7\f\2\2\u00f4\u00f3\3\2\2\2\u00f4\u00f5\3\2\2\2"+
		"\u00f5\u00f8\3\2\2\2\u00f6\u00f8\7\f\2\2\u00f7\u00f2\3\2\2\2\u00f7\u00f6"+
		"\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\b\24\2\2\u00fa(\3\2\2\2\u00fb"+
		"\u00fc\7\61\2\2\u00fc\u00fd\7,\2\2\u00fd\u0101\3\2\2\2\u00fe\u0100\13"+
		"\2\2\2\u00ff\u00fe\3\2\2\2\u0100\u0103\3\2\2\2\u0101\u0102\3\2\2\2\u0101"+
		"\u00ff\3\2\2\2\u0102\u0104\3\2\2\2\u0103\u0101\3\2\2\2\u0104\u0105\7,"+
		"\2\2\u0105\u0106\7\61\2\2\u0106\u0107\3\2\2\2\u0107\u0108\b\25\2\2\u0108"+
		"*\3\2\2\2\u0109\u010a\7\61\2\2\u010a\u010b\7\61\2\2\u010b\u010f\3\2\2"+
		"\2\u010c\u010e\n\3\2\2\u010d\u010c\3\2\2\2\u010e\u0111\3\2\2\2\u010f\u010d"+
		"\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u010f\3\2\2\2\u0112"+
		"\u0113\b\26\2\2\u0113,\3\2\2\2\u0114\u0115\7v\2\2\u0115\u0116\7t\2\2\u0116"+
		"\u0117\7w\2\2\u0117\u011e\7g\2\2\u0118\u0119\7h\2\2\u0119\u011a\7c\2\2"+
		"\u011a\u011b\7n\2\2\u011b\u011c\7u\2\2\u011c\u011e\7g\2\2\u011d\u0114"+
		"\3\2\2\2\u011d\u0118\3\2\2\2\u011e.\3\2\2\2\u011f\u0123\5\61\31\2\u0120"+
		"\u0122\5\u008dG\2\u0121\u0120\3\2\2\2\u0122\u0125\3\2\2\2\u0123\u0121"+
		"\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0128\3\2\2\2\u0125\u0123\3\2\2\2\u0126"+
		"\u0128\7\62\2\2\u0127\u011f\3\2\2\2\u0127\u0126\3\2\2\2\u0128\60\3\2\2"+
		"\2\u0129\u012a\t\4\2\2\u012a\62\3\2\2\2\u012b\u012c\7)\2\2\u012c\u012d"+
		"\5\65\33\2\u012d\u012e\7)\2\2\u012e\64\3\2\2\2\u012f\u0131\5\67\34\2\u0130"+
		"\u012f\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0133\3\2"+
		"\2\2\u0133\66\3\2\2\2\u0134\u0137\n\5\2\2\u0135\u0137\5? \2\u0136\u0134"+
		"\3\2\2\2\u0136\u0135\3\2\2\2\u01378\3\2\2\2\u0138\u013a\7$\2\2\u0139\u013b"+
		"\5;\36\2\u013a\u0139\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013c\3\2\2\2\u013c"+
		"\u013d\7$\2\2\u013d:\3\2\2\2\u013e\u0140\5=\37\2\u013f\u013e\3\2\2\2\u0140"+
		"\u0141\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142<\3\2\2\2"+
		"\u0143\u0146\n\6\2\2\u0144\u0146\5? \2\u0145\u0143\3\2\2\2\u0145\u0144"+
		"\3\2\2\2\u0146>\3\2\2\2\u0147\u0148\5A!\2\u0148@\3\2\2\2\u0149\u014a\7"+
		"^\2\2\u014a\u014b\t\7\2\2\u014bB\3\2\2\2\u014c\u014d\7p\2\2\u014d\u014e"+
		"\7w\2\2\u014e\u014f\7n\2\2\u014f\u0150\7n\2\2\u0150D\3\2\2\2\u0151\u0152"+
		"\7-\2\2\u0152F\3\2\2\2\u0153\u0154\7-\2\2\u0154\u0155\7-\2\2\u0155H\3"+
		"\2\2\2\u0156\u0157\7/\2\2\u0157J\3\2\2\2\u0158\u0159\7/\2\2\u0159\u015a"+
		"\7/\2\2\u015aL\3\2\2\2\u015b\u015c\7,\2\2\u015cN\3\2\2\2\u015d\u015e\7"+
		"\61\2\2\u015eP\3\2\2\2\u015f\u0160\7\'\2\2\u0160R\3\2\2\2\u0161\u0162"+
		"\7>\2\2\u0162T\3\2\2\2\u0163\u0164\7>\2\2\u0164\u0165\7?\2\2\u0165V\3"+
		"\2\2\2\u0166\u0167\7@\2\2\u0167X\3\2\2\2\u0168\u0169\7@\2\2\u0169\u016a"+
		"\7?\2\2\u016aZ\3\2\2\2\u016b\u016c\7>\2\2\u016c\u016d\7>\2\2\u016d\\\3"+
		"\2\2\2\u016e\u016f\7@\2\2\u016f\u0170\7@\2\2\u0170^\3\2\2\2\u0171\u0172"+
		"\7*\2\2\u0172`\3\2\2\2\u0173\u0174\7+\2\2\u0174b\3\2\2\2\u0175\u0176\7"+
		"]\2\2\u0176d\3\2\2\2\u0177\u0178\7_\2\2\u0178f\3\2\2\2\u0179\u017a\7}"+
		"\2\2\u017ah\3\2\2\2\u017b\u017c\7\177\2\2\u017cj\3\2\2\2\u017d\u017e\7"+
		"(\2\2\u017el\3\2\2\2\u017f\u0180\7~\2\2\u0180n\3\2\2\2\u0181\u0182\7("+
		"\2\2\u0182\u0183\7(\2\2\u0183p\3\2\2\2\u0184\u0185\7~\2\2\u0185\u0186"+
		"\7~\2\2\u0186r\3\2\2\2\u0187\u0188\7`\2\2\u0188t\3\2\2\2\u0189\u018a\7"+
		"#\2\2\u018av\3\2\2\2\u018b\u018c\7\u0080\2\2\u018cx\3\2\2\2\u018d\u018e"+
		"\7A\2\2\u018ez\3\2\2\2\u018f\u0190\7<\2\2\u0190|\3\2\2\2\u0191\u0192\7"+
		"=\2\2\u0192~\3\2\2\2\u0193\u0194\7.\2\2\u0194\u0080\3\2\2\2\u0195\u0196"+
		"\7?\2\2\u0196\u0082\3\2\2\2\u0197\u0198\7?\2\2\u0198\u0199\7?\2\2\u0199"+
		"\u0084\3\2\2\2\u019a\u019b\7#\2\2\u019b\u019c\7?\2\2\u019c\u0086\3\2\2"+
		"\2\u019d\u019e\7\60\2\2\u019e\u0088\3\2\2\2\u019f\u01a4\5\u008bF\2\u01a0"+
		"\u01a3\5\u008bF\2\u01a1\u01a3\5\u008dG\2\u01a2\u01a0\3\2\2\2\u01a2\u01a1"+
		"\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5"+
		"\u008a\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a8\t\b\2\2\u01a8\u008c\3\2"+
		"\2\2\u01a9\u01aa\t\t\2\2\u01aa\u008e\3\2\2\2\22\2\u00ee\u00f4\u00f7\u0101"+
		"\u010f\u011d\u0123\u0127\u0132\u0136\u013a\u0141\u0145\u01a2\u01a4\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}