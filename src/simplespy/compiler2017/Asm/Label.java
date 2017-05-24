package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 5/16/17.
 */
public class Label extends Assembly {
    Symbol symbol;
    public Label(){
        symbol = new Symbol(null);
    }

    public Label(String name){
        symbol = new Symbol(name);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public String toString() {
        return symbol.toString();
    }
}
