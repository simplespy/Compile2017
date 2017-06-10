package simplespy.compiler2017.Asm;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by spy on 5/18/17.
 */
public class Register extends Operand {
    RegisterClass registerClass;
    AsmType type;
    public enum RegisterClass {
        AX, BX, CX, DX, SI, DI, SP, BP, AL,CL, R8,  R9,  R10,  R11,  R12,  R13,  R14, R15

    }
    public static Register bp = new PhiReg(Register.RegisterClass.BP);
    public static Register sp = new PhiReg(Register.RegisterClass.SP);
    public static Register ax = new PhiReg(Register.RegisterClass.AX);
    public static Register cx = new PhiReg(Register.RegisterClass.CX);
    public static Register dx = new PhiReg(Register.RegisterClass.DX);

    public static Register di = new PhiReg(Register.RegisterClass.DI);
    public static Register si = new PhiReg(Register.RegisterClass.SI);
    public static Register bx = new PhiReg(Register.RegisterClass.BX);

    public static Register r10 = new PhiReg(Register.RegisterClass.R10);
    public static Register r11 = new PhiReg(Register.RegisterClass.R11);
    public static Register r12 = new PhiReg(Register.RegisterClass.R12);
    public static Register r13 = new PhiReg(Register.RegisterClass.R13);
    public static Register r14 = new PhiReg(Register.RegisterClass.R14);
    public static Register r15 = new PhiReg(Register.RegisterClass.R15);
    public static Register r8 = new PhiReg(Register.RegisterClass.R8);
    public static Register r9 = new PhiReg(Register.RegisterClass.R9);



    public Register(RegisterClass rc, AsmType type){
        this.registerClass = rc;
        this.type = type;
    }
    public Register(){
        this(null);
    }

    public Register(RegisterClass rc){
        this(rc,AsmType.INT64);
    }

    public void collectStatistics(Statistics stats) {
        stats.registerUsed(this);
    }

    @Override
    public void fixOffset(int i) {

    }

    @Override
    public String toString() {
        String reg = registerClass.toString().toLowerCase();
        if (registerClass.ordinal() >= RegisterClass.R8.ordinal()) {
            return reg;
        }
        if (reg.equals("al")) return "al";
        if (reg.equals("cl")) return "cl";

        switch (type){
            case INT8:
                if (reg.lastIndexOf('x') == 1) reg.replace('x','l');
                else reg += 'l';
                break;
            case INT32:
                reg = 'e'+reg;
                break;
            case INT64:
                reg = 'r'+reg;
                break;


        }
        return reg;
    }



    /**
     * Created by spy on 5/22/17.
     */
    public enum AsmType {
        INT8, INT16, INT32, INT64
    }

    @Override
    public List<Register> getRegisters(List<Register> registers) {
        registers.add(this);
        return registers;
    }

    public int getID(){
        return -1;
    }
    class LifeSpan{
        public int begin = 10000000;
        public int end = 0;
    }
    LifeSpan lifeSpan = new LifeSpan();

    public void setLifeSpan(int old) {
        lifeSpan.begin = Math.min(lifeSpan.begin, old);
        lifeSpan.end = Math.max(lifeSpan.end, old);

    }
    public void printLifeSpan(PrintStream out){
        out.print("Reg" + getID() + " : ");
        for (int i = 0; i <= lifeSpan.end; ++i){
            if (i == lifeSpan.begin || i == lifeSpan.end) out.print(i);
            else if(i < lifeSpan.begin) out.print(' ');
            else  out.print('-');
        }
        out.println();
    }
}


