package simplespy.compiler2017.BackEnd.SIR;


import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.BackEnd.ASMVisitor;
import simplespy.compiler2017.NodeFamily.BinaryOpNode;
import simplespy.compiler2017.NodeFamily.UnaryOpNode;
import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.util.*;

/**
 * Created by spy on 17/6/7.
 */
public class Function {
    public String name;
    public List<Instruction> instructions;
    public Map<Label, Instruction> labelInstructionMap = new LinkedHashMap<>();
    public Map<Register, Operand> registerMap = new LinkedHashMap<>();
    public List<VarDecNode> paras = new ArrayList<>();
    public Label epilogue;
    public int parameterSavedWord;
    public ArrayList<Register> registerList = new ArrayList<>();
    public HashSet<Register> usedReg = new HashSet<>();
    public int caller = 0;
    public int callee = 0;


    public Function(String name){
        VirReg.num = 0;
        instructions = new ArrayList<>();
        this.name = name;
    }
    public void mov(Operand src, Operand dest){
        if (src instanceof MemoryReference && dest instanceof MemoryReference){
            VirReg tmp =  new VirReg(this);
            instructions.add(new Move(src, tmp));
            instructions.add(new Move(tmp, dest));
        }
        else instructions.add(new Move(src, dest));
    }
    public void bin(BinaryOpNode.BinaryOp op, Operand left, Operand right) {instructions.add(new Binary(op, left, right));}
    public void branch(Operand condition, Label then, Label otherwise){ instructions.add(new Branch(condition, then, otherwise));}
    public void jmp(Label label){ instructions.add(new Jmp(label));}
    public void label(Label label){
        Labelline labelline = new Labelline(label);
        instructions.add(labelline);
        labelInstructionMap.put(label, labelline);
    }
    public void uni(UnaryOpNode.UnaryOp op, Operand value){ instructions.add(new Unary(op, value));}
    public void call(Operand func, List<Operand> paras){instructions.add(new CallFunc(func, paras));}

    public void call(Operand func, List<Operand> paras, Operand result){instructions.add(new CallFunc(func, paras, result));}
    public void put(VirReg virReg){
        registerMap.put(virReg, null);
    }
    public void put(Register virReg, Operand phiReg){
        registerMap.replace(virReg, phiReg);
    }
    public void put(Register virReg, IndirectMemoryReference address){
        registerMap.replace(virReg, address);
    }
    public void comment(String comment){ instructions.get(instructions.size()-1).setComment(comment);}
    public void save_caller(){instructions.add(new Instruction("save_caller"));}
    public Instruction getLastIns() {
        return instructions.get(instructions.size()-1);
    }


}
