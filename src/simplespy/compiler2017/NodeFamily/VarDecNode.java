package simplespy.compiler2017.NodeFamily;


import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.IRPrinter;
import simplespy.compiler2017.NodeFamily.IRNode.Expr;

/**
 * Created by spy on 17/3/25.
 */
public class VarDecNode extends ASTBranch {
    public String name;
    public TypeNode type;
    public ExprNode init;
    public final Location loc;
    public Expr ir;
    public MemoryReference memoryReference;
    Symbol address;

    public void setAddress(Symbol address) {
        this.address = address;
    }

    public Symbol getAddress() {
        return address;
    }

    public void setMemoryReference(MemoryReference memoryReference) {
        this.memoryReference = memoryReference;
    }

    public MemoryReference getMemoryReference() {
        return memoryReference;
    }

    public VarDecNode(TypeNode type, String name, ExprNode init, Location loc){
        this.type = type;
        this.name = name;
        this.init = init;
        this.loc = loc;
    }
    public VarDecNode(TypeNode type){
        this.type = type;
        this.loc = null;
    }


    public Location getLoc() {
        return loc;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(IRPrinter visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public TypeNode getType() {
        return type;
    }

    public void setIr(Expr ir) {
        this.ir = ir;
    }
    static private long tmpSeq = 0;

    static public VarDecNode tmp(TypeNode t) {
        return new VarDecNode(t, "@tmp" + tmpSeq++, null, null);
    }
}
