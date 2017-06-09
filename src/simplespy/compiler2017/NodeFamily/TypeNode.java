package simplespy.compiler2017.NodeFamily;

/**
 * Created by spy on 17/3/26.
 */
public abstract class TypeNode extends Node {

    public enum TYPENAME {
        INT, BOOL, STRING, VOID, ARRAY, STRUCT, FUNCTION, NULL
    }

    public TYPENAME typename;

    abstract public TypeNode getBaseType();

    public TYPENAME getTypeName() {
        return typename;
    }
    public int size(){
        switch (this.toString()){
            case "INT":
                return 4;
            case "BOOL":
                return 2;
            case "VOID":
                return 1;
            default:
                return 8;
        }
    }
    public boolean isString(){
        return false;
    }

}
