package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.Instruction;
import simplespy.compiler2017.Asm.Register;
import simplespy.compiler2017.BackEnd.SIR.Function;
import simplespy.compiler2017.BackEnd.SIR.Move;

import java.io.PrintStream;

/**
 * Created by spy on 17/6/7.
 */
public class Coloring {
    Function function;
    final static int MAXM = 1000;
    Graph interferenceGraph;

    public Coloring(Function function){
        this.function = function;
    }

    public void build(){
        interferenceGraph = new Graph(function);
        interferenceGraph.build();
    }
    public void print(){
        interferenceGraph.printGraph(System.out);
        interferenceGraph.Coalesce();
    }

    class Graph{
        int[][] matrix;
        int size;
        Function func;
        int[] degree;
        int[][] neighbor;
        public Graph(Function func){
            this.func = func;
            size = func.registerMap.keySet().size();
            matrix = new int[size][size];
            degree = new int[size];
            neighbor = new int[size][];
            resetMatrix();
        }
        private void resetMatrix(){
            neighbor = new int[size][];
            for (int i = 0; i < size; ++i)
                for (int j = 0; j < size; ++j)
                    matrix[i][j] = 0;
        }
        private void createMatrix(){
            int old = 0;
            for (Instruction ins : func.instructions){
                for (Register i : ins.out){
                    i.setLifeSpan(old);
                    ++old;
                    for (Register j : ins.out){
                        if (i.getID() == j.getID()) continue;
                        if(i.getID() < 0 || j.getID() < 0) continue;
                        if(matrix[i.getID()][j.getID()] == 0){
                            ++degree[i.getID()];
                            ++degree[j.getID()];
                            matrix[i.getID()][j.getID()] = 1;
                        }
                        else matrix[j.getID()][i.getID()] = 1;
                    }
                }
            }
        }
        public void printGraph(PrintStream out){
            out.println("--------LifeSpan---------");
            func.registerMap.keySet().stream().forEachOrdered(x->x.printLifeSpan(out));

            out.println("--------Matrix---------");


        }
        public void createTabel(){
            for (int i = 0; i < size; ++i){
                neighbor[i] = new int[degree[i]];
            }
            for (int i = 0; i < size; ++i){
                int k = 0;
                for (int j = 0; j < size; ++j){
                    if (matrix[i][j] == 1) {
                        neighbor[i][k] = j;
                        ++k;
                    }
                }
            }

        }
        private boolean interfere(int x, int y){
            return  matrix[x][y] == 1 ;
        }

        public void build(){
            createMatrix();
            createTabel();
        }

        public void Coalesce(){
            for (Instruction ins : func.instructions){
                if (ins instanceof Move && ins.use.size() != 0){
                    int src = ins.use.get(0).getID();
                    int dest = ins.def.get(0).getID();
                    if (src >= 0 && dest >= 0 && !interfere(src, dest)){
                        System.out.println(src + "~" + dest);
                    }
                }
            }

        }
    }

}
