package simplespy.compiler2017.Asm;

import java.util.List;
import java.util.Set;

/**
 * Created by spy on 5/18/17.
 */
abstract public class Operand implements OperandPattern {
    abstract public void collectStatistics(Statistics stats);
    public boolean match(Operand operand) {
        return equals(operand);
    }
    public abstract void fixOffset(int i);
    public List<Register> getRegisters(List<Register> registers){
        return registers;
    }
    public boolean isReg(){
        return false;
    }
    public boolean isMem(){
        return false;
    }
}
