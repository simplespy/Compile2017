package simplespy.compiler2017.BackEnd.SIR;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.BackEnd.Coloring;
import simplespy.compiler2017.FrontEnd.GlobalScope;
import simplespy.compiler2017.FrontEnd.TypeTable;
import simplespy.compiler2017.NodeFamily.FuncDefNode;
import simplespy.compiler2017.NodeFamily.VarDecNode;

import java.util.*;

/**
 * Created by spy on 17/6/7.
 */
public class SIR {
    public List<Function> functionList = new ArrayList<>();
    public List<VarDecNode> vars = new LinkedList<>();//global variables
    public GlobalScope scope;
    public Map<String, FuncDefNode> funcs = new LinkedHashMap<>();//global functions
    public TypeTable typeTable;//class

    public void Print(){
        functionList.stream().forEachOrdered(x->{
            System.out.println(x.name+':');
            x.instructions.stream().forEachOrdered(y->{
                if (y instanceof Labelline){
                    System.out.println(y.toString()+':');
                }
                else System.out.println('\t'+y.toString());

            }
                    );
        });
    }

    public void PrintNext(){
        functionList.stream().forEachOrdered(x->{
            System.out.println(x.name+':');
            x.instructions.stream().forEachOrdered(y->{
                System.out.print(Integer.toString(x.instructions.indexOf(y))+'\t');
                if (y instanceof Labelline) System.out.print(y.toString()+':');
                else System.out.print('\t'+y.toString());
                System.out.print("\tnext:");
                for (Instruction succ : y.next) {
                    while (succ instanceof Jmp) succ = x.labelInstructionMap.get(((Jmp) succ).label);
                    while (succ instanceof Labelline) succ = succ.next.get(0);
                    System.out.print(Integer.toString(x.instructions.indexOf(succ))+' ');
                }System.out.println();

            });
        });
    }

    public void PrintInOut(){
        functionList.stream().forEachOrdered(x->{
            System.out.println(x.name+':');
            x.instructions.stream().forEachOrdered(y->{
                System.out.print(String.format("%1$-5s", Integer.toString(x.instructions.indexOf(y))+'\t'));
                if (y instanceof Labelline) System.out.print(String.format("%1$-25s", y.toString()+':'));
                else System.out.print(String.format("%1$-25s",'\t'+y.toString()));
                System.out.print("\t\t\tnext:");
                String next = "";
                for (Instruction succ : y.next) {
                    while (succ instanceof Jmp) succ = x.labelInstructionMap.get(((Jmp) succ).label);
                    while (succ instanceof Labelline) {
                        if (succ.equals(x.labelInstructionMap.get(x.epilogue))) break;
                        succ = succ.next.get(0);
                    }
                    next = next.concat(Integer.toString(x.instructions.indexOf(succ))+' ');
                }System.out.print(String.format("%1$-7s", next));

                String buffer = "";
                System.out.print("\tdef:");
                for (Register in : y.def) {
                    buffer = buffer.concat(in.toString()+' ');
                }
                System.out.print(String.format("%1$-7s", buffer));
                buffer = "";
                System.out.print("\t\tuse:");
                for (Register in : y.use) {
                    buffer = buffer.concat(in.toString()+' ');
                }
                System.out.print(String.format("%1$-10s", buffer));
                buffer = "";
                System.out.print("\t\tin:");
                for (Register in : y.in) {
                    buffer = buffer.concat(in.toString()+' ');
                }
                System.out.print(String.format("%1$-18s", buffer));
                buffer = "";
                System.out.print("\t\tout:");
                for (Register out : y.out) {
                    buffer = buffer.concat(out.toString()+' ');
                }
                System.out.println(String.format("%1$-18s", buffer));

            });
        });
    }

    public void createGraph(){
        functionList.stream().forEachOrdered(x->{
            for (int i = 0; i < x.instructions.size()-1; ++i){
                Instruction ins = x.instructions.get(i);
                Instruction next = x.instructions.get(i+1);
                if (ins instanceof Branch){
                    ins.next.add(next);
                    ins.next.add(x.labelInstructionMap.get(((Branch) ins).otherwise));
                }/*else if (ins instanceof Jmp){
                    ins.next.add(x.labelInstructionMap.get(((Jmp) ins).label));
                }*/
                else ins.next.add(next);
            }
        });
    }

    public void AnalyzeLiveness(){
        final int[] rnd = {0};
        functionList.stream().forEachOrdered(func->{
            func.instructions.stream().forEachOrdered(Instruction::setDefUse);
            boolean done = false;
            do{
                done = true;
                for (Instruction ins : func.instructions){
                    Set<Register> newin = new HashSet<>(ins.in);
                    Set<Register> newout = new HashSet<>(ins.out);

                    newin.addAll(ins.out);newin.removeAll(ins.def);newin.addAll(ins.use);//in[n] = use[n]+(out[n]-def[n])

                    for (Instruction succ : ins.next) {
                        while (succ instanceof Jmp) succ = func.labelInstructionMap.get(((Jmp) succ).label);
                        while (succ instanceof Labelline){
                            if (succ.equals(func.labelInstructionMap.get(func.epilogue))) break;
                            succ = succ.next.get(0);
                        }
                        newout.addAll(succ.in);
                    }

                    if (!newin.equals(ins.in)) done = false;
                    ins.in = newin;
                    if (!newout.equals(ins.out)) done = false;
                    ins.out = newout;
                }
              //  System.out.println("--------------- "+Integer.toString(++rnd[0])+"Rounds -------------------");
                //PrintInOut();
            }while(!done);
        });

    }
    public void run(){
        createGraph();
        AnalyzeLiveness();
    }

    public void PrintAllocateResult(){
        for (Function func : functionList){
             for (Register reg : func.registerMap.keySet()){
                System.out.println(reg.toString() + " ~ " + func.registerMap.get(reg).toString());
         }
        }

    }

}
