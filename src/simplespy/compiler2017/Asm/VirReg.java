package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.SIR.Function;

import java.io.PrintStream;

/**
 * Created by spy on 17/6/7.
 */
public class VirReg extends Register {
    static public int num = 0;
    public int id;
    public VirReg(){
        id = num++;
    }
    public VirReg(Function outer){
        id = num++;
        outer.put(this);
    }
    @Override
    public String toString() {
        return  "Reg" + id;
    }

    @Override
    public int getID() {
        return id;
    }

    public void printLifeSpan(PrintStream out){
        out.print("Reg" + id + " : ");
        for (int i = 0; i <= lifeSpan.end; ++i){
            if (i == lifeSpan.begin || i == lifeSpan.end) out.print(i);
            else if(i < lifeSpan.begin) out.print(' ');
            else  out.print('-');
        }
        out.println();
    }

}
