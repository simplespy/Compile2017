package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/21/17.
 */
public class Symbol extends Operand {
    public String name;

    public Symbol(String name) {
        if (name == null){
                name = AssemblyCode.table.symbolString(this);;
        }
        this.name = name;
    }

    @Override
    public String toString() {


        return name;
    }
    public void collectStatistics(Statistics stats) {
        stats.symbolUsed(this);
    }

    @Override
    public void fixOffset(int i) {

    }
}
