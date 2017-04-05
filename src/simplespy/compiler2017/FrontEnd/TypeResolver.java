package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;


/**
 * Created by spy on 17/3/30.
 */
public class TypeResolver implements ASTVisitor {
     private TypeTable typeTable;
    private LocalScope currentClass;
    private String className;
    private GlobalScope gl;

    @Override
    public void visit(ASTRoot node) {
        typeTable = node.typeTable;
        gl = node.globalScope;
        node.branches.stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(ASTBranch node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ClassDefNode node) {
        LocalScope localScope = (LocalScope) typeTable.getScope(node.getName());
        currentClass = localScope;
        className = node.getName();
        node.getMembers().stream().forEachOrdered(this::visit);
        currentClass = null;
    }

    @Override
    public void visit(FuncDefNode node) {
        node.type = node.returnType;
        visit(node.getReturnType());
        node.parameters.stream().forEachOrdered(this::visit);

        visit(node.body);
    }

    @Override
    public void visit(VarDecNode node)  {
        TypeNode base = node.getType().getBaseType();
        visit(node.type);
        if (base.toString().equals( "VOID")) {
            CompilationError.exceptions.add(new SemanticException("Void Variable is invalid" + node.getLoc().toString()));
        }
        visit(node.init);
       /* if (node.init != null && node.init.getType() != node.getType().type) {
            CompilationError.exceptions.add(new SemanticException("Unmatched Type when initialized " + node.getLoc().toString()));

        }*/


    }

    @Override
    public void visit(TypeNode node) {
        if (node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(ArrayType node) {
        if (node.getBaseType().toString().equals("VOID"))
            CompilationError.exceptions.add(new SemanticException("Void Array at " + node.getLoc().toString()));

    }

    @Override
    public void visit(ClassType node) {
        String name = node.name;
        if (!typeTable.find(name) )
            CompilationError.exceptions.add(new SemanticException("Undeclared Class " + name  + node.getLoc().toString()));
    }

    @Override
    public void visit(BaseType node) {

    }

    @Override
    public void visit(ConstructorNode node) {
        if (!node.getName().equals(className)){
            CompilationError.exceptions.add(new SemanticException("Unmatched Constructor "   + node.getLoc().toString()));

        }
        visit(node.body);

    }

    @Override
    public void visit(StmtNode node) {
        if(node == null) return;
        node.accept(this);
    }

    @Override
    public void visit(BlockNode node) {
        /*List<VarDecNode> vars = new ArrayList<>();
        node.getStmts().stream().filter(x->x instanceof VarDecInBlockNode)
                                .forEachOrdered(x->vars.add(((VarDecInBlockNode)x).getVardec()));
        pushScope(vars);*/
        node.getStmts().stream().forEachOrdered(this::visit);
    }

    @Override
    public void visit(VarDecInBlockNode node) {
        visit(node.vardec);
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
        TypeNode type = node.getType();

        for (int i = 0; i < node.item.size(); ++i) {
            ExprNode it = node.item.get(i);
            if (it != null) {
                visit(it);
                if (it.getType().toString() != "INT") {
                    CompilationError.exceptions.add(new SemanticException("Dimension expression in a new-expression should be INT"));
                    return;
                }
            }
            type = new ArrayType(type, it, node.getLoc());

        }

        node.type = type;
    }

    @Override
    public void visit(IDNode node) {
        try {
            Node ent = node.scope.get(node.name);
            node.setEntity(ent);
            node.type = node.entity.getType();
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

        if (node.expr.getType() instanceof ClassType){
            node.member.setScope(typeTable.getScope(node.expr.getType().toString()));
        }
        else if (node.expr.getType().toString().equals("STRING")){
            node.member.setScope(gl.getStringscope());
        }
        else if (node.expr.getType() instanceof ArrayType) {
            if (node.member.getName().equals("size")) {
                node.type = new BaseType(TypeNode.TYPENAME.INT, node.getLoc());
                node.member.scope = gl.array;
            }return;
        } else{
            CompilationError.exceptions.add(new SemanticException("Can't Access Member here"));
            return;
        }
        visit(node.member);
    }

    @Override
    public void visit(ThisNode node) {
        if (currentClass == null){
            CompilationError.exceptions.add( new SemanticException("This can only be in the class " + node.getLoc().toString()));
            return;
        }
        node.setScope(currentClass);
        node.type = typeTable.classTypeMap.get(className);


    }

    @Override
    public void visit(FuncallNode node) {
        visit(node.name);
        node.parameters.stream().forEachOrdered(this::visit);


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
