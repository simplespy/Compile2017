package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/21/17.
 */
public class Symbol extends Operand {
    public String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (name == null)
            return AssemblyCode.table.symbolString(this);;

        return name;
    }
    public void collectStatistics(Statistics stats) {
        stats.symbolUsed(this);
    }

    @Override
    public void fixOffset(int i) {

    }
}
