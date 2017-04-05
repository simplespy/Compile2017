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
}
