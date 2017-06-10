package simplespy.compiler2017;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import simplespy.compiler2017.Asm.AssemblyCode;
import simplespy.compiler2017.BackEnd.*;
import simplespy.compiler2017.BackEnd.SIR.SIR;
import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.FrontEnd.*;
import simplespy.compiler2017.NodeFamily.ASTRoot;
import simplespy.compiler2017.NodeFamily.IRNode.IRRoot;
import simplespy.compiler2017.Parser.SimpilerLexer;
import simplespy.compiler2017.Parser.SimpilerParser;


import java.io.*;

/**
 * Created by spy on 17/3/22.
 */
public class MidTerm {
    public static void main(String[] argv) throws Exception {
        try {
            InputStream is = System.in;
        //    InputStream is = new FileInputStream("Test/SingleStmt.txt");//System.in;
            CompilationError.initialize();
        //    PrintStream os = new PrintStream("/Users/spy/programs/x86/first.asm");

            ANTLRInputStream input = new ANTLRInputStream(is);
            SimpilerLexer lexer = new SimpilerLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SimpilerParser parser = new SimpilerParser(tokens);
            parser.setErrorHandler(new BailErrorStrategy());

            ParseTree tree = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();
            ASTBuilder builder = new ASTBuilder();
            walker.walk(builder, tree);
            ASTRoot ast = builder.getAst();

        //    ASTPrinter printer = new ASTPrinter(System.out);
        //    ast.accept(printer);

//
            ScopeBuilder scopeBuilder = new ScopeBuilder();
            ast.accept(scopeBuilder);

            TypeResolver typeResolver = new TypeResolver();
            ast.accept(typeResolver);

            DereferenceChecker DChecker = new DereferenceChecker();
            ast.accept(DChecker);

        //    CompilationError.printExceptions();

            if (!CompilationError.exceptions.isEmpty()) {
                CompilationError.printExceptions();
                throw new Exception();
            }



        } catch (Exception e) {
            System.exit(1);
        }
    }

}
