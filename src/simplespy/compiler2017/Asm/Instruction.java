package simplespy.compiler2017.Asm;


import simplespy.compiler2017.BackEnd.ASMVisitor;
import simplespy.compiler2017.Utils.ListUtils;

import java.util.Arrays;

/**
 * Created by spy on 5/18/17.
 */
public class Instruction extends Assembly {
    protected String op;
    protected Operand[] operands;
    protected boolean needRelocation;

    public Instruction(String op, Operand src, Operand dest){
        this.op = op;
        this.operands = new Operand[] {src, dest};
    }
    public Instruction(String op, Operand ope){
        this.op = op;
        this.operands = new Operand[] {ope};
    }
    public Instruction(String op){
        this.op = op;
    }

    public void collectStatistics(Statistics stats) {
        stats.instructionUsed(op);
        for (int i = 0; i < operands.length; i++) {
            operands[i].collectStatistics(stats);
        }
    }

    @Override
    public String toString() {
        String line = op;
        if (operands == null) return line;
        for (int i = operands.length-1; i >= 0; --i){
            if (i == operands.length-1) line += " ";
            else line += ", ";
            line += operands[i].toString();
        }

        return line;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
