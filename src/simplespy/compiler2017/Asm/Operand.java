package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/18/17.
 */
abstract public class Operand {
    abstract public void collectStatistics(Statistics stats);

    public abstract void fixOffset(int i);
}
