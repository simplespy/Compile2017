package simplespy.compiler2017.NodeFamily;

import simplespy.compiler2017.Asm.IndirectMemoryReference;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.FrontEnd.ASTVisitor;
import simplespy.compiler2017.NodeFamily.IRNode.Expr;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/3/25.
 */
public class ClassDefNode extends ASTBranch {
    public final String name;
    public final List<ASTBranch> members;//Var or Func
    public final Location loc;
    public Map<String, FuncDefNode> funcs = new LinkedHashMap<>();
    public List<VarDecNode> vars = new ArrayList<>();
    public ConstructorNode constructor;

    public Location getLoc() {
        return loc;
    }
    public ClassDefNode(String name, Location loc){
        this.name = name;
        this.loc = loc;
        members = new ArrayList<>();
    }
    public void add(Object member){
        if(member instanceof ASTBranch) members.add((ASTBranch) member);
        else if (member instanceof List) ((List) member).stream().forEachOrdered(this::add);
    }

    public String getName() {
        return name;
    }

    public List<ASTBranch> getMembers() {
        return members;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public int getOffset(IDNode member){
        int offset = 0;
        for (ASTBranch branch : members){
            if (branch instanceof VarDecNode){
                ++offset;
                String name = ((VarDecNode) branch).name;
                if (name.equals(member.name)) return offset;
            }
        }
        throw new Error("there is noe such member");
    }

    public void addFunc(FuncDefNode func){
        this.funcs.put(func.name, func);
    }
    public void addVar(VarDecNode var){
        this.vars.add(var);
        var.externClass = this;
        var.setMemoryReference(new IndirectMemoryReference(this.getOffset(var)*8, new Register(Register.RegisterClass.R13)));
    }
    public int getOffset(VarDecNode var){
        return vars.indexOf(var);
    }
    public int getMemorySize(){
        return vars.size();
    }
}
