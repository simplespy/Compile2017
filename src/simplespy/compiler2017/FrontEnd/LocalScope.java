package simplespy.compiler2017.FrontEnd;


import simplespy.compiler2017.NodeFamily.Node;

import java.util.LinkedHashMap;

/**
 * Created by spy on 17/3/30.
 */
public class LocalScope extends Scope {
    protected Scope parent;


    public LocalScope(Scope parent){
        entities = new LinkedHashMap<>();
        this.parent = parent;
        parent.addChild(this);
    }

    @Override
    public Node get(String name){
        Node var = entities.get(name);
        if (var != null){
            return var;
        }else{
            return parent.get(name);
        }
    }
}
