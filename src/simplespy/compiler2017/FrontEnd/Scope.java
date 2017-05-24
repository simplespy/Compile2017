package simplespy.compiler2017.FrontEnd;

import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.Node;
import simplespy.compiler2017.NodeFamily.TypeNode;
import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/3/30.
 */
public abstract class Scope {
    Map<String, Node> entities;
    protected List<Scope> children;
    public abstract Node get(String name);
    public Scope(){
        children = new ArrayList<>();
        entities = new LinkedHashMap<>();
    }

    public void addEntity(Node entity){
        Node e = entities.get(entity.getName());
        if (e != null) {
            CompilationError.exceptions.add( new SemanticException(
                    "Duplicated Declaration: "
                            + entity.getName() + ": "
                            + e.getLoc().toString()
                            + " and "
                            + entity.getLoc().toString()));
        }
        entities.put(entity.getName(), entity);
        entity.setScope(this);
    }
    public void addChild(Scope chi){
        children.add(chi);
    }

    public Map<String, Node> getEntities() {
        return entities;
    }

    public List<Scope> getChildren() {
        return children;
    }

    public VarDecNode allocateTmp(TypeNode t) {
        VarDecNode var = VarDecNode.tmp(t);
        this.addEntity(var);
        return var;
    }}
