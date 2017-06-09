package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.PhiReg;
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



}
