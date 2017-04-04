package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.Exception.SemanticException;

import java.util.List;
import java.util.Stack;

/**
 * Created by spy on 17/3/30.
 */
public class ScopeBuilder implements ASTVisitor {
     private TypeTable typeTable;
    public final Stack<Scope> scopeStack;
    private LocalScope currentClass;
    private String className;
    private GlobalScope gl;


    public ScopeBuilder(){
        scopeStack = new Stack<>();
    }

    private void pushScope(List<VarDecNode> vars){
        LocalScope scope = new LocalScope(scopeStack.peek());
        vars.stream().forEachOrdered(scope::addEntity);
        scopeStack.push(scope);
    }





    @Override
    public void visit(ASTRoot node) {

        typeTable = node.typeTable;
        gl = node.globalScope;
        scopeStack.push(node.globalScope);
        node.branches.stream().forEachOrdered(node.globalScope::addEntity);
        node.branches.stream().forEachOrdered(this::visit);
        node.setScope(node.globalScope);

    }

    @Override
    public void visit(ASTBranch node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ClassDefNode node) {
        LocalScope localScope = (LocalScope) typeTable.getScope(node.getName());
        scopeStack.push(localScope);
        currentClass = localScope;
        className = node.getName();
        node.getMembers().stream().forEachOrdered(localScope::addEntity);
        node.getMembers().stream().forEachOrdered(this::visit);
        node.setScope(scopeStack.pop());
        currentClass = null;
    }

    @Override
    public void visit(FuncDefNode node) {
        pushScope(node.parameters);
        visit(node.body);
        node.setScope(scopeStack.pop());

    }

    @Override
    public void visit(VarDecNode node)  {
        node.setScope(scopeStack.peek());
        TypeNode base = node.getType().getBaseType();
        if (base instanceof ClassType){
            String name = ((ClassType) base).name;
            if (!typeTable.find(name) )
                CompilationError.exceptions.add(new SemanticException("Undeclared Class " + name  + node.getLoc().toString()));
        }
        if (base.toString().equals( "VOID")) {
            CompilationError.exceptions.add(new SemanticException("Void Variable is invalid" + node.getLoc().toString()));
            System.exit(1);
        }

        visit(node.init);

       /* if (node.init != null && node.init.getType() != node.getType().type) {
            CompilationError.exceptions.add(new SemanticException("Unmatched Type when initialized " + node.getLoc().toString()));

        }*/


    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(ArrayType node) {
        if (node.getBaseType().getType().toString().equals("VOID"))
            CompilationError.exceptions.add(new SemanticException("Void Array at " + node.getLoc().toString()));

    }

    @Override
    public void visit(ClassType node) {

    }

    @Override
    public void visit(BaseType node) {

    }

    @Override
    public void visit(ConstructorNode node) {
        visit(node.body);

    }

    @Override
    public void visit(StmtNode node) {
        if(node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(BlockNode node) {
        LocalScope localScope = new LocalScope(scopeStack.peek());
        scopeStack.push(localScope);
        /*List<VarDecNode> vars = new ArrayList<>();
        node.getStmts().stream().filter(x->x instanceof VarDecInBlockNode)
                                .forEachOrdered(x->vars.add(((VarDecInBlockNode)x).getVardec()));
        pushScope(vars);*/
        node.getStmts().stream().forEachOrdered(this::visit);
        node.setScope(scopeStack.pop());

    }

    @Override
    public void visit(VarDecInBlockNode node) {
        visit(node.vardec);
        scopeStack.peek().addEntity(node);

      /*  TypeNode base = node.getVardec().getType().getBaseType();
        if (base instanceof ClassType) {
            String name = ((ClassType) base).name;
            if (!typeTable.find(name)) {
                CompilationError.exceptions.add(new SemanticException("Undeclared Class " + name + node.getLoc().toString()));
                return;
            }
        }
        if (base.type == TypeNode.TYPENAME.VOID) {
            CompilationError.exceptions.add(new SemanticException("Void Variable is invalid" + node.getLoc().toString()));
            System.exit(1);
        }

        scopeStack.peek().addEntity(node);*/

    }

    @Override
    public void visit(WhileNode node) {
        visit(node.condition);
        visit(node.body);

    }

    @Override
    public void visit(ForNode node) {
        visit(node.init);
        visit(node.condition);
        visit(node.step);

    }

    @Override
    public void visit(ReturnNode node) {
        visit(node.value);

    }

    @Override
    public void visit(IfNode node) {
        visit(node.condition);
        visit(node.then);
        visit(node.otherwise);

    }

    @Override
    public void visit(BreakNode node) {

    }

    @Override
    public void visit(ContinueNode node) {

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

    }

    @Override
    public void visit(UnaryOpNode node) {
        visit(node.body);
    }

    @Override
    public void visit(SuffixOpNode node) {
        visit(node.expr);
    }

    @Override
    public void visit(NewNode node) {
        node.setScope(scopeStack.peek());
        node.item.stream().forEachOrdered(this::visit);

    }

    @Override
    public void visit(IDNode node) {
        try {
            node.setScope(scopeStack.peek());
            Node ent = scopeStack.peek().get(node.name);
            ent.referred();
            node.setEntity(ent);
            node.type = ent.getType();
        }catch (Exception whatever){
            CompilationError.exceptions.add( new SemanticException("ID-Node Error at " + node.getLoc().toString()));
        }
    }

    @Override
    public void visit(ArefNode node) {
        visit(node.expr);

    }

    @Override
    public void visit(MemberNode node) {
        visit(node.expr);

        if (node.expr instanceof ThisNode) {
            if (currentClass == null){
                CompilationError.exceptions.add( new SemanticException("This can only be in the class " + node.getLoc().toString()));
                return;
            }
            node.setScope(currentClass);
            node.member.setScope(currentClass);
            visit(node.member);
            return;
        }
        if (node.expr.getType() instanceof ClassType){
            scopeStack.push(typeTable.getScope(node.expr.getType().toString()));
            visit(node.member);
            node.setScope(scopeStack.pop());
        }
        else if (node.expr.getType().toString().equals("STRING")){

            scopeStack.push(gl.getStringscope());
            visit(node.member);
            node.setScope(scopeStack.pop());
        }
        else if (node.expr.getType() instanceof ArrayType) {
            if (node.member.getName().equals("size")) {
                node.setScope(scopeStack.peek());
                node.type = new BaseType(TypeNode.TYPENAME.INT, node.getLoc());

            }
        }
        else{
            CompilationError.exceptions.add(new SemanticException("Can't Access Member here"));
        }
    }

    @Override
    public void visit(ThisNode node) {
        node.setScope(currentClass);
        node.type = typeTable.classTypeMap.get(className);


    }

    @Override
    public void visit(FuncallNode node) {
        visit(node.name);

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
