package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/3/30.
 */
public class GlobalScope extends Scope {
    public LocalScope string;
    public LocalScope array;

    public GlobalScope(){
        children = new ArrayList<>();
        entities = new LinkedHashMap<>();
        addFunctions();
        addStringScope();
        addArrayScope();
    }

    private void addFunctions(){
        ArrayList<VarDecNode> paras = new ArrayList<>();
        VarDecNode para = new VarDecNode(new BaseType(TypeNode.TYPENAME.STRING, null), "str", null, null);
        paras.add(para);
        ArrayList<VarDecNode> paras2 = new ArrayList<>();
        VarDecNode para2 = new VarDecNode(new BaseType(TypeNode.TYPENAME.INT, null), "str", null, null);
        paras2.add(para2);

        FuncDefNode print = new FuncDefNode(new BaseType(TypeNode.TYPENAME.VOID, null), "print", paras, new BlockNode(null), null);
        FuncDefNode println = new FuncDefNode(new BaseType(TypeNode.TYPENAME.VOID, null), "println", paras, new BlockNode(null), null);
        FuncDefNode getString = new FuncDefNode(new BaseType(TypeNode.TYPENAME.STRING, null), "getString", new ArrayList<>(), new BlockNode(null), null);
        FuncDefNode getInt = new FuncDefNode(new BaseType(TypeNode.TYPENAME.INT, null), "getInt", new ArrayList<>(), new BlockNode(null), null);
        FuncDefNode toString = new FuncDefNode(new BaseType(TypeNode.TYPENAME.STRING, null), "toString", paras2, new BlockNode(null), null);

        addEntity(print);
        addEntity(println);
        addEntity(getString);
        addEntity(getInt);
        addEntity(toString);



    }

    private void addStringScope(){
        string = new LocalScope(this);
        List<VarDecNode> paras = new ArrayList<>();
        VarDecNode para = new VarDecNode(new BaseType(TypeNode.TYPENAME.INT, null), "pos", null, null);
        paras.add(para);
        ArrayList<VarDecNode> paras2 = new ArrayList<>();
        VarDecNode para2 = new VarDecNode(new BaseType(TypeNode.TYPENAME.INT, null), "left", null, null);
        VarDecNode para3 = new VarDecNode(new BaseType(TypeNode.TYPENAME.INT, null), "right", null, null);

        paras2.add(para2);
        paras2.add(para3);
        FuncDefNode length = new FuncDefNode(new BaseType(TypeNode.TYPENAME.INT, null), "length", new ArrayList<>(), new BlockNode(null), null);
        FuncDefNode parseInt = new FuncDefNode(new BaseType(TypeNode.TYPENAME.INT, null), "parseInt", new ArrayList<>(), new BlockNode(null), null);
        FuncDefNode ord = new FuncDefNode(new BaseType(TypeNode.TYPENAME.INT, null), "ord", paras, new BlockNode(null), null);
        FuncDefNode substring = new FuncDefNode(new BaseType(TypeNode.TYPENAME.STRING, null), "substring", paras2, new BlockNode(null), null);
        string.addEntity(length);
        string.addEntity(parseInt);
        string.addEntity(ord);
        string.addEntity(substring);

        ClassDefNode string = new ClassDefNode("String", null);
        length.externClass = string;
        parseInt.externClass = string;
        ord.externClass = string;
        substring.externClass = string;

    }

    private void addArrayScope(){
        array = new LocalScope(this);
        FuncDefNode size = new FuncDefNode(new BaseType(TypeNode.TYPENAME.INT, null), "size", new ArrayList<>(), new BlockNode(null), null);
        array.addEntity(size);
    }

    public LocalScope getStringscope(){
        return string;
    }

    @Override
    public Node get(String name){
        Node var = entities.get(name);
        if (var != null){
            return var;
        }else{
            CompilationError.exceptions.add( new SemanticException("Unresolved Reference: " + name));
            return null;
        }

    }

}
