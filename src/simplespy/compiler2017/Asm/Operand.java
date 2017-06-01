package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/18/17.
 */
abstract public class Operand implements OperandPattern {
    abstract public void collectStatistics(Statistics stats);
    public boolean match(Operand operand) {
        return equals(operand);
    }
    public abstract void fixOffset(int i);
}
