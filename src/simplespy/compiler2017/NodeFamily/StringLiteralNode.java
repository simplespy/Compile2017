package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.Asm.DirectMemoryReference;
import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class StringLiteralNode extends ExprNode{
    public final String value;
    Symbol address;
    public boolean isGlobal;
    public MemoryReference memoryReference;


    public void setMemoryReference(MemoryReference memoryReference) {
        this.memoryReference = memoryReference;
    }

    public MemoryReference getMemoryReference() {
        return memoryReference;
    }

    public StringLiteralNode(String value) {
        this.value = value;
        type = new BaseType(TypeNode.TYPENAME.STRING, null);
        isLv = false;
        isGlobal = false;
    }

    public Symbol getAddress() {
        return address;
    }

    public void setAddress(Symbol address) {
        this.address = address;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return false;
    }
}
