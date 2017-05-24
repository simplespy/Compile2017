package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 5/18/17.
 */
public class Comment extends Assembly {
    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
