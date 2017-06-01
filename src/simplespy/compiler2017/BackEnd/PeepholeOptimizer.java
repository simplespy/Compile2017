package simplespy.compiler2017.BackEnd;

import simplespy.compiler2017.Asm.*;
import simplespy.compiler2017.Utils.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by spy on 17/6/1.
 */
public class PeepholeOptimizer {
    private Map<String, List<Filter>> filterSet;

    public PeepholeOptimizer() {
        this.filterSet = new HashMap<String, List<Filter>>();
    }

    public void add(Filter filter) {
        String[] heads = filter.patternHeads();
        for (int i = 0; i < heads.length; i++) {
            String head = heads[i];
            List<Filter> list = filterSet.get(head);
            if (list == null) {
                list = new ArrayList<Filter>();
                list.add(filter);
                filterSet.put(head, list);
            }
            else {
                list.add(filter);
            }
        }
    }

    public List<Assembly> optimize(List<Assembly> assemblies) {
        List<Assembly> result = new ArrayList<Assembly>();
        Cursor<Assembly> cursor = new Cursor<Assembly>(assemblies);
        while (cursor.hasNext()) {
            Assembly asm = cursor.next();
            if (asm instanceof Instruction) {
                Filter matched = matchFilter(cursor);
                if (matched != null) {
                    matched.optimize(cursor, result);
                    continue;
                }
            }
            result.add(asm);
        }
        return result;
    }

    private Filter matchFilter(Cursor<Assembly> asms) {
        Instruction insn = (Instruction)asms.current();
        List<Filter> filters = filterSet.get(insn.op);
        if (filters == null) return null;
        if (filters.isEmpty()) return null;
        for (Filter filter : filters) {
            if (filter.match(asms)) {
                return filter;
            }
        }
        return null;
    }

    static public PeepholeOptimizer defaultSet() {
        PeepholeOptimizer set = new PeepholeOptimizer();
        set.loadDefaultFilters();
        return set;
    }

    private void loadDefaultFilters() {
        PeepholeOptimizer set = this;

        // mov
        set.add(new SingleInsnFilter(
                new InsnPattern("mov", new ImmediateValue(0), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("xor", insn.operands[1], insn.operands[0]);
                    }
                }
        ));

        // add
        set.add(new SingleInsnFilter(
                new InsnPattern("add", new ImmediateValue(-1), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("dec", insn.operands[1]);
                    }
                }
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("add", new ImmediateValue(0), reg()),
                null
        ));
        // #@@range/pattern{
        set.add(new SingleInsnFilter(
                new InsnPattern("add", new ImmediateValue(1), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("inc", insn.operands[1]);
                    }
                }
        ));
        // #@@}

        // sub
        set.add(new SingleInsnFilter(
                new InsnPattern("sub", new ImmediateValue(-1), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("inc", insn.operands[1]);
                    }
                }
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("sub", new ImmediateValue(0), reg()),
                null
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("sub", new ImmediateValue(1), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("dec", insn.operands[1]);
                    }
                }
        ));

        // imul
        set.add(new SingleInsnFilter(
                new InsnPattern("imul", new ImmediateValue(0), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("xor", insn.operands[1], insn.operands[1]);
                    }
                }
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("imul", new ImmediateValue(1), reg()),
                null
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("imul", new ImmediateValue(2), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("sal", new ImmediateValue(1), insn.operands[1]);
                    }
                }
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("imul", new ImmediateValue(4), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("sal", new ImmediateValue(2), insn.operands[1]);
                    }
                }
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("imul", new ImmediateValue(8), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("sal", new ImmediateValue(3), insn.operands[1]);
                    }
                }
        ));
        set.add(new SingleInsnFilter(
                new InsnPattern("imul", new ImmediateValue(16), reg()),
                new InsnTransform() {
                    public Instruction apply(Instruction insn) {
                        return new Instruction("sal", new ImmediateValue(4), insn.operands[1]);
                    }}
        ));

        // jmp
        set.add(new JumpEliminationFilter());
    }



    private OperandPattern reg() {
        return new AnyRegisterPattern();
    }

    abstract class Filter {
        abstract public String[] patternHeads();
        abstract public boolean match(Cursor<Assembly> asms);
        abstract public void optimize(Cursor<Assembly> src, List<Assembly> dest);
    }

    //
    // single instruction optimization
    //

    class SingleInsnFilter extends Filter {
        private InsnPattern pattern;
        private InsnTransform transform;

        public SingleInsnFilter(InsnPattern pattern, InsnTransform transform) {
            this.pattern = pattern;
            this.transform = transform;
        }

        /** Matching op of InstructionPattern */
        public String[] patternHeads() {
            return new String[] { pattern.name };
        }

        public boolean match(Cursor<Assembly> asms) {
            return pattern.match((Instruction)asms.current());
        }

        public void optimize(Cursor<Assembly> src, List<Assembly> dest) {
            if (transform == null) {
                ;   // remove instruction
            }
            else {
                dest.add(transform.apply((Instruction)src.current()));
            }
        }
    }

    class InsnPattern {
        private String name;
        private OperandPattern pattern1;
        private OperandPattern pattern2;

        InsnPattern(String name, OperandPattern pat1, OperandPattern pat2) {
            this.name = name;
            this.pattern1 = pat1;
            this.pattern2 = pat2;
        }

        public boolean match(Instruction insn) {
            return name.equals(insn.op)
                    && (pattern1 == null || pattern1.match(insn.operands[0]))
                    && (pattern2 == null || pattern2.match(insn.operands[1]));
        }
    }

    class AnyRegisterPattern implements OperandPattern {
        public boolean match(Operand operand) {
            return (operand instanceof Register);
        }
    }

    interface InsnTransform {
        abstract public Instruction apply(Instruction insn);
    }


    class JumpEliminationFilter extends Filter {
        public JumpEliminationFilter() {
        }

        private String[] jmpInsns() {
            return new String[] { "jmp", "jz", "jne", "je", "jne" };
        }

        public String[] patternHeads() {
            return jmpInsns();
        }

        public void optimize(Cursor<Assembly> src, List<Assembly> dest) {
            ;   // remove jump
        }

        public boolean match(Cursor<Assembly> asms) {
            Instruction insn = (Instruction)asms.current();
            return doesLabelFollows(asms.clone(), insn.jmpDestination());
        }


        private boolean doesLabelFollows(Cursor<Assembly> asms, Symbol jmpDest) {
            while (asms.hasNext()) {
                Assembly asm = asms.next();
                if (asm instanceof Label) {
                    Label label = (Label)asm;
                    if (label.getSymbol().equals(jmpDest)) {
                        return true;
                    }
                    else {
                        continue;
                    }
                }

                else {
                    return false;
                }
            }
            return false;
        }
    }
}
