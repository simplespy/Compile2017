package simplespy.compiler2017.Asm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by spy on 5/24/17.
 */
public class SymbolTable {
    protected String base;
    protected Map<Symbol, String> map;
    protected long seq = 0;

    static private final String DUMMY_SYMBOL_BASE = "L";
    static private final SymbolTable dummy = new SymbolTable(DUMMY_SYMBOL_BASE);


    public SymbolTable(String base) {
        this.base = base;
        this.map = new HashMap<>();
    }

    public Symbol newSymbol() {
        return new Symbol(newString());
    }

    public String symbolString(Symbol sym) {
        String str = map.get(sym);
        if (str != null) {
            return str;
        }
        else {
            String newStr = newString();
            sym.name = newStr;
            map.put(sym, newStr);
            return newStr;
        }
    }

    protected String newString() {
        return base + seq++;
    }
}
