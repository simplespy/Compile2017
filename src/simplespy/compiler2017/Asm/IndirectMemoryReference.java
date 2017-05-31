package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/23/17.
 */
public class IndirectMemoryReference extends MemoryReference {
    int offset;
    Register base;
    boolean fixed;
    public IndirectMemoryReference(int offset, Register base, boolean fixed) {
        this.offset = offset;
        this.base = base;
        this.fixed = fixed;
    }
    public IndirectMemoryReference(int offset, Register base) {
        this(offset, base, true);
    }
    static public IndirectMemoryReference relocatable(int offset, Register base) {
        return new IndirectMemoryReference(offset, base, false);
    }
    @Override
    public String toString() {
        if (offset == 0) return '['+base.toString()+']';
        return '['+base.toString()+(offset>0?'+':'-')+Integer.toString(Math.abs(offset))+']';
    }
    public void collectStatistics(Statistics stats) {
        base.collectStatistics(stats);
    }
    public void fixOffset(int diff) {
        this.offset += diff;
    }
}
