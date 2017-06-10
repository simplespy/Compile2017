package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.PhiReg;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.Asm.VirReg;
import simplespy.compiler2017.BackEnd.SIR.*;

import java.util.*;

/**
 * Created by spy on 17/6/8.
 */
public class SimpleAllocator {
    private SIR ir;
    private Function func;
    private List<PhiReg> regs = new ArrayList<>();


    public SimpleAllocator(SIR ir, Collection<PhiReg> regs) {
        this.ir = ir;
        this.regs.addAll(regs);
    }

    class SimpleGraph{
        Map<Register, ArrayList<Register>> interferenceChain;
        Function function;
        public SimpleGraph(Function function){
            interferenceChain = new HashMap<>();
            this.function = function;
        }
        public void build(){
            for (Instruction ins : function.instructions){
                if (ins.def.isEmpty()) continue;
                Register def = ins.def.get(0);

            }
        }
    }



}
