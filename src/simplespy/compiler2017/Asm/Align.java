package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 17/5/29.
 */
public class Align extends Assembly {
    String command;
    public boolean isadded;
    public Align(int kind, boolean isadded){
        if (kind == 0){
            command = "sub rsp, 8";
        }
        else {
            command = "add rsp, 8";
        }
        this.isadded = isadded;
    }
    @Override
    public String toString() {
        return command;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
