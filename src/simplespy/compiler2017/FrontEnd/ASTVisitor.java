package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;

/**
 * Created by spy on 17/3/25.
 */
public interface ASTVisitor {
    void visit(ASTRoot node) ;

    void visit(ASTBranch node);

    void visit(ClassDefNode node);
    void visit(FuncDefNode node);
    void visit(VarDecNode node);

    void visit(TypeNode node);

    void visit(ArrayType node);
    void visit(ClassType node);
    void visit(BaseType node);

    void visit(ConstructorNode node);

    void visit(StmtNode node) ;

    void visit(BlockNode node);

    void visit(VarDecInBlockNode node);
    void visit(WhileNode node);
    void visit(ForNode node);
    void visit(ReturnNode node);
    void visit(IfNode node);
    void visit(BreakNode node);
    void visit(ContinueNode node);

    void visit(ExprNode node);

    void visit(BinaryOpNode node);
    void visit(UnaryOpNode node);
    void visit(SuffixOpNode node);
    void visit(NewNode node);
    void visit(IDNode node);
    void visit(ArefNode node);
    void visit(MemberNode node);
    void visit(ThisNode node);
    void visit(FuncallNode node);

    void visit(IntLiteralNode node);
    void visit(BoolLiteralNode node);
    void visit(StringLiteralNode node);
    void visit(NullLiteralNode node);
    void visit(EmptyNode node);



}
