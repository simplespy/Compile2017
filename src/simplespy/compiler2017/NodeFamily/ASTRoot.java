package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.FrontEnd.GlobalScope;
import simplespy.compiler2017.FrontEnd.TypeTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spy on 17/3/24.
 */
public class ASTRoot extends Node{
    public final List<ASTBranch> branches;
    public TypeTable typeTable;
    public GlobalScope globalScope;


    public ASTRoot() {
        branches = new ArrayList<>();
        typeTable = new TypeTable();
        globalScope = new GlobalScope();
    }

    public void add(Object branch){
        if(branch instanceof ASTBranch) branches.add((ASTBranch) branch);
        else if (branch instanceof List) ((List) branch).stream().forEachOrdered(this::add);
    }


    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }


}
