package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/23/17.
 */
public class IndirectMemoryReference extends MemoryReference {
    int offset;
    Register base;


    public IndirectMemoryReference(int offset, Register base) {
        this.offset = offset;
        this.base = base;
    }

    @Override
    public String toString() {
        if (offset == 0) return '['+base.toString()+']';
        return '['+base.toString()+Integer.toString(offset)+']';
    }
    public void collectStatistics(Statistics stats) {
        base.collectStatistics(stats);
    }
    public void fixOffset(int diff) {
        this.offset += diff;
    }
}
