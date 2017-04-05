package simplespy.compiler2017;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.FrontEnd.*;
import simplespy.compiler2017.NodeFamily.ASTRoot;
import simplespy.compiler2017.Parser.SimpilerLexer;
import simplespy.compiler2017.Parser.SimpilerParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;

/**
 * Created by spy on 17/3/25.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        /*String sdir = "Test/";
        File dir = new File(sdir);
        String[] children = dir.list();
        if (children == null) {
            System.out.println( "No directory");
        }
        else {
            for (int i = 0; i < children.length; ++i) {
                String filename = children[i];

                if (!filename.contains(".txt")) continue;
                System.out.println(filename);

                InputStream is = new FileInputStream(sdir+filename); // or System.in;
                PrintStream os = new PrintStream("Output/"+filename.substring(0, filename.length()-3)+"out");
                CompilationError.initialize();

                boolean succ = true;
     //           try {
                    ANTLRInputStream input = new ANTLRInputStream(is);
                    SimpilerLexer lexer = new SimpilerLexer(input);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    SimpilerParser parser = new SimpilerParser(tokens);
                    ParseTree tree = parser.program();

                    ParseTreeWalker walker = new ParseTreeWalker();
                    ASTBuilder builder = new ASTBuilder();
                    walker.walk(builder, tree);
                    ASTRoot ast = builder.getAst();

                    //ASTPrinter printer = new ASTPrinter(os);
                    //ast.accept(printer);

                    ScopeBuilder scopeBuilder = new ScopeBuilder();
                    ast.accept(scopeBuilder);

                    TypeResolver typeResolver = new TypeResolver();
                    ast.accept(typeResolver);

                    DereferenceChecker DChecker = new DereferenceChecker();
                    ast.accept(DChecker);

                   // CompilationError.printExceptions();
             //       if (!CompilationError.exceptions.isEmpty()) throw new Exception();
               // }catch (Exception whatever){
                 //   System.exit(1);
                  //  System.out.println(filename + "   failed");
                  //  succ = false;
                //}
                //if (succ) System.out.println(filename + "   passed");*/
        // try {
        //InputStream is = new FileInputStream("Test/SingleStmt.txt");
        boolean pass = true;

        String sdir = "Test/";
        File dir = new File(sdir);
        String[] children = dir.list();
        if (children == null) {
            System.out.println("No directory");
        } else {
            for (int i = 0; i < children.length; ++i) {
                String filename = children[i];


                if (!filename.contains(".txt")) continue;
                System.out.println(filename);
                InputStream is = new FileInputStream(sdir + filename); // or System.in;
                CompilationError.initialize();

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

                //ASTPrinter printer = new ASTPrinter(os);
                //ast.accept(printer);

                ScopeBuilder scopeBuilder = new ScopeBuilder();
                ast.accept(scopeBuilder);

                TypeResolver typeResolver = new TypeResolver();
                ast.accept(typeResolver);

                DereferenceChecker DChecker = new DereferenceChecker();
                ast.accept(DChecker);

                CompilationError.printExceptions();

                if (!CompilationError.exceptions.isEmpty()) {
                    CompilationError.printExceptions();
                    throw new Exception();
                }


            }
        }
    }
}

