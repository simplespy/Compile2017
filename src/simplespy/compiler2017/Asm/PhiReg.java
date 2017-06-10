package simplespy.compiler2017.Asm;

/**
 * Created by spy on 17/6/7.
 */
public class PhiReg extends Register {
    public PhiReg(RegisterClass rc) {
        super(rc);
    }

    @Override
    public boolean isReg() {
        return true;
    }

}
