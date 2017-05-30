package simplespy.compiler2017.Asm;

/**
 * Created by spy on 5/18/17.
 */
public class Register extends Operand {
    RegisterClass registerClass;
    AsmType type;
    public enum RegisterClass {
        AX, BX, CX, DX, SI, DI, SP, BP, AL,CL, R0,  R1,  R2,  R3,  R4,  R5,  R6,  R7,  R8,  R9,  R10,  R11,  R12,  R13,  R14, R15

    }
    public Register(RegisterClass rc, AsmType type){
        this.registerClass = rc;
        this.type = type;
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
        if (registerClass.ordinal() >= RegisterClass.R0.ordinal()) {
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

    public RegisterClass getRegisterClass() {
        return registerClass;
    }
}


