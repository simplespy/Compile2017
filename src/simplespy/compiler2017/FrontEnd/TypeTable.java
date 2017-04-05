package simplespy.compiler2017.FrontEnd;


import simplespy.compiler2017.Exception.CompilationError;
import simplespy.compiler2017.Exception.SemanticException;
import simplespy.compiler2017.NodeFamily.*;

import java.util.*;

/**
 * Created by spy on 17/3/31.
 */
public class TypeTable {
    Map<String, ClassType> classTypeMap;
    Map<String, Scope> classScope;

    public TypeTable() {
        classTypeMap = new LinkedHashMap<>();
        classScope = new LinkedHashMap<>();
    }



    public void put(String name, ClassType type, Scope scope)  {
        if (classTypeMap.containsKey(name)) {
            CompilationError.exceptions.add(new SemanticException("Class " + name + " Redeclaration"));
            return;
        }
        classTypeMap.put(name, type);
        classScope.put(name, scope);

    }



    public boolean find(String name){
        if (classTypeMap.containsKey(name)){
            return true;
        }else return false;
    }

    public Scope getScope(String name){
        return classScope.get(name);
    }

    public void printClassList(){
        classTypeMap.keySet().stream().forEachOrdered(System.out::println);
    }

}
