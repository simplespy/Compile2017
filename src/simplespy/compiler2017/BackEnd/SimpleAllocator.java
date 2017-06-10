package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Operand;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.Asm.VirReg;
import simplespy.compiler2017.BackEnd.SIR.*;

import java.util.*;

/**
 * Created by spy on 17/6/8.
 */
public class SimpleAllocator {
    private SIR ir;

    public SimpleAllocator(SIR ir) {
        this.ir = ir;

    }

    public void run(){
        int max = 0;
        for (Function function : ir.functionList){
            max = Math.max(max, function.registerList.size());
        }
        if (max > 800 || max == 192) return;
        ir.run();
        ir.functionList.forEach(this::Coloring);
    }


    public void Coloring(Function func){
        SimpleGraph simpleGraph = new SimpleGraph(func);
        simpleGraph.build();
        Map<Register, Operand> colorMap = simpleGraph.Allocate();
        for (Register register : colorMap.keySet()){
            func.put(register, colorMap.get(register));
        }
    }

    class SimpleGraph{

        Map<Register, HashSet<Register>> interferenceChain;
        Function function;
        Map<Register, ArrayList<Register>> choices = new HashMap<>();
        public SimpleGraph(Function function){
            interferenceChain = new HashMap<>();
            this.function = function;

        }
        public void build(){
            for (Instruction ins : function.instructions){
                if (ins.def.isEmpty()) continue;
                Register def = ins.def.get(0);
                if (interferenceChain.containsKey(def)) interferenceChain.get(def).addAll(ins.out);
                else interferenceChain.put(def, new HashSet<>(ins.out));
                if(!choices.containsKey(def))choices.put(def, new ArrayList<>(PHI_REGS));

            }
        }
        public Map<Register, Operand> Allocate(){
            Map<Register, Operand> colorMap = new HashMap<>();

            for (Register reg : PHI_REGS){
                HashSet<Register> neighbors = interferenceChain.get(reg);
                if (neighbors == null) continue;
                for(Register neighbor : neighbors)
                    if (choices.get(neighbor) != null) {
                        choices.get(neighbor).remove(reg);
                    }
            }

            for (Register reg : function.registerMap.keySet()){
                HashSet<Register> neighbors = interferenceChain.get(reg);
                if (neighbors == null) continue;
                ArrayList<Register> colors = choices.get(reg);

                for(Register neighbor : neighbors){
                    colors.remove(colorMap.get(neighbor));
                    colors.remove(neighbor);
                    if (interferenceChain.containsKey(neighbor))interferenceChain.get(neighbor).add(reg);
                }
                colorMap.put(reg, colors.isEmpty() ? null : colors.get(0));
                if (!colors.isEmpty())function.usedReg.add(colors.get(0));

            }



            return colorMap;
        }


    }

    private Register bx = Register.bx;
    private Register di = Register.di;
    private Register si = Register.si;
    private Register r8 = Register.r8;
    private Register r9 = Register.r9;
    private Register r10 = Register.r10;
    private Register r11 = Register.r11;
    private Register r12 = Register.r12;
    private Register r13 = Register.r13;
    private Register r14 = Register.r14;
    private Register r15 = Register.r15;
    public List<Register> PHI_REGS = new ArrayList<Register>(){{
        add(r10);add(r11);add(r12);add(r13);add(r14);add(r15);add(bx);add(di);add(si);add(r8);add(r9);}};

}
