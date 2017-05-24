package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/23/17.
 */
public class DirectMemoryReference extends MemoryReference {
    public Symbol value;
    public DirectMemoryReference(Symbol val) {
        this.value = val;
    }
    public void collectStatistics(Statistics stats) {
        value.collectStatistics(stats);
    }
    public void fixOffset(long diff) {
        throw new Error("DirectMemoryReference#fixOffset");
    }
    public String toString() {
        return value.name;
    }


}
