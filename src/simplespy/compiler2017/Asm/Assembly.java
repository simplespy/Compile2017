package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.ASMVisitor;

/**
 * Created by spy on 5/16/17.
 */
abstract public class Assembly  {
    abstract public void accept(ASMVisitor visitor);
    public void collectStatistics(Statistics stats) {}
    public String comment;
    public void setComment(String comment) {
        this.comment = comment;
    }

}
