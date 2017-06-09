package simplespy.compiler2017.Asm;

import java.util.Set;

/**
 * Created by spy on 5/18/17.
 */
public class MemoryReference extends Operand {
    public Operand addr;

    public MemoryReference(){}
    public MemoryReference(Operand addr){
        this.addr = addr;
    }

    @Override
    public void collectStatistics(Statistics stats) {

    }

    @Override
    public void fixOffset(int i) {

    }

    @Override
    public String toString() {
        return "qword" +'['+ addr.toString() + ']';
    }

    @Override
    public Set<Register> getRegisters(Set<Register> registers) {
        if (addr == null) return registers;
        return addr.getRegisters(registers);
    }

    @Override
    public boolean isMem() {
        return true;
    }
}
