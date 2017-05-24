package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.NodeFamily.*;
import simplespy.compiler2017.Exception.SemanticException;

import java.net.IDN;
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


    private void checkMain(){
        Node main = gl.get("main");
        if (gl == null){
            CompilationError.exceptions.add(new SemanticException("Program doesn't have main function"));
        }
        else if (main instanceof FuncDefNode && !((FuncDefNode)main).getReturnType().toString().equals("INT")){
            CompilationError.exceptions.add(new SemanticException("main function must be INT type"));
        }else if (main instanceof FuncDefNode && ((FuncDefNode)main).parameters.size() != 0){
            CompilationError.exceptions.add(new SemanticException("main function can't have parameters"));
        }
    }


    @Override
    public void visit(ASTRoot node) {
        typeTable = node.typeTable;
        gl = node.globalScope;
        scopeStack.push(node.globalScope);
        node.branches.stream().forEachOrdered(x->{gl.addEntity(x); visit(x);});

       // node.branches.stream().forEachOrdered(node.globalScope::addEntity);
     //   node.branches.stream().forEachOrdered(this::visit);
        checkMain();
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
        scopeStack.pop();
        currentClass = null;
    }

    @Override
    public void visit(FuncDefNode node) {
        pushScope(node.parameters);
        node.body.getStmts().stream().forEachOrdered(x->x.setScope(scopeStack.peek()));
        node.setScope(scopeStack.peek());
        node.body.setScope(scopeStack.peek());
        node.body.getStmts().stream().forEachOrdered(this::visit);
        scopeStack.pop();

    }

    @Override
    public void visit(VarDecNode node)  {
        visit(node.init);
    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(ArrayType node) {
       /* if (node.getBaseType().getType().toString().equals("VOID"))
            CompilationError.exceptions.add(new SemanticException("Void Array at " + node.getLoc().toString()));
*/
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

        node.getStmts().stream().forEachOrdered(x->x.setScope(scopeStack.peek()));
        node.getStmts().stream().forEachOrdered(this::visit);
        node.setScope(scopeStack.pop());

    }

    @Override
    public void visit(VarDecInBlockNode node) {
        visit(node.vardec);
        scopeStack.peek().addEntity(node);
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
        visit(node.body);
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
    public void visit(BreakNode node) {}

    @Override
    public void visit(ContinueNode node) {}

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
        node.item.stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(IDNode node) {
        try {
            node.setScope(scopeStack.peek());
            Node ent = scopeStack.peek().get(node.name);
            node.setEntity(ent);
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
        }
    }

    @Override
    public void visit(ThisNode node) {
        node.setScope(currentClass);
        node.type = typeTable.classTypeMap.get(className);


    }

    @Override
    public void visit(FuncallNode node) {
        node.setScope(scopeStack.peek());
        node.name.setScope(scopeStack.peek());
        if (!(node.name instanceof IDNode)){
            visit(node.name);
        }
        node.parameters.stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(IntLiteralNode node) {}

    @Override
    public void visit(BoolLiteralNode node) {}

    @Override
    public void visit(StringLiteralNode node) {
        typeTable.putString(node.value,node);
    }

    @Override
    public void visit(NullLiteralNode node) {}

    @Override
    public void visit(EmptyNode node) {}
}
