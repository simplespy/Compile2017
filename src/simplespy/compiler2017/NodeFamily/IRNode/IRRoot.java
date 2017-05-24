package simplespy.compiler2017.NodeFamily.IRNode;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.GlobalScope;
import simplespy.compiler2017.FrontEnd.IRVisitor;
import simplespy.compiler2017.FrontEnd.TypeTable;
import simplespy.compiler2017.NodeFamily.FuncDefNode;
import simplespy.compiler2017.NodeFamily.Node;
import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/4/12.
 */
public class IRRoot{
    public List<VarDecNode> vars = new LinkedList<>();
    public GlobalScope scope;
    public Map<String, FuncDefNode> funcs = new LinkedHashMap<>();
    public TypeTable typeTable;

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }


}
