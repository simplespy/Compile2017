package simplespy.compiler2017.Utils;

/**
 * Created by spy on 5/23/17.
 */
public final class AsmUtils {
    private AsmUtils() {}

    static public int align(int n, int alignment) {
        return (n + alignment - 1) / alignment * alignment;
    }
}
