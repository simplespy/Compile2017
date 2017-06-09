package simplespy.compiler2017.Asm;

import simplespy.compiler2017.BackEnd.SIR.Function;

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
}
