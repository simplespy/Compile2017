package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.Asm.DirectMemoryReference;
import simplespy.compiler2017.Asm.ImmediateValue;
import simplespy.compiler2017.Asm.MemoryReference;
import simplespy.compiler2017.Asm.Symbol;
import simplespy.compiler2017.FrontEnd.ASTVisitor;

/**
 * Created by spy on 17/3/27.
 */
public class StringLiteralNode extends ExprNode{
    public final String value;
    Symbol symbol;
    public boolean isGlobal;
    public MemoryReference memoryReference;
    public ImmediateValue address;


    public void setMemoryReference(MemoryReference memoryReference) {
        this.memoryReference = memoryReference;
    }

    public MemoryReference getMemoryReference() {
        return memoryReference;
    }

    public StringLiteralNode(String value) {
        if (value.charAt(0) == '"'){
            value = value.substring(1);
        }
        if (value.charAt(value.length()-1) == '"'){
            value = value.substring(0,value.length()-1);
        }
        this.value = value;
        type = new BaseType(TypeNode.TYPENAME.STRING, null);
        isLv = false;
        isGlobal = false;
    }

    public Symbol getAddress() {
        return symbol;
    }

    public void setAddress(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean Lv() {
        return false;
    }
    public boolean isConstant(){
        return true;
    }
}
