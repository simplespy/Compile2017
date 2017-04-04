package simplespy.compiler2017.NodeFamily;

/**
 * Created by spy on 17/3/24.
 */
public class Location {
    public final int line;
    public final int column;

    public Location(int line, int column){
        this.line = line;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return " (" + getLine() + "," + getColumn() + ")";
    }
}
