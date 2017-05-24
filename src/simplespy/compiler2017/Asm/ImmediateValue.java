package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/18/17.
 */
public class ImmediateValue extends Operand {
    public int value;

    public ImmediateValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
    public void collectStatistics(Statistics stats) {
        // does nothing
    }

    @Override
    public void fixOffset(int i) {

    }
}
