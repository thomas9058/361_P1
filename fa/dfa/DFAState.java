package fa.dfa;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import fa.State;

/**
 * concrete state used by DFA
 * 
 * @author Dalton Bilau Goncalves, Thomas Pengelly
 */
public class DFAState extends State {

    /** one row of delta for this state: symbol -> destination */
    private final Map<Character, DFAState> row = new LinkedHashMap<>();

    /**
     * constructs a DFA with given name
     * 
     * @param name the name of the state
     */
    public DFAState(String name) {
        super(name);
    }

    /**
     * creates or overwrites a transition delta(this, sym) = dest
     * 
     * @param sym the input symbol
     * @param dest the destination state
     */
    public void put(char sym, DFAState dest) {
        row.put(sym, dest);
    }

    /**
     * returns the destination of delta(this, sym)
     * 
     * @param sym the input symbol
     * @return the destination state
     */
    public DFAState next(char sym) {
        return row.get(sym);
    }

    /**
     * returns entire transition row for given state
     * 
     * @return the map of symbol -> desination for this state
     */
    public Map<Character, DFAState> row() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DFAState)) return false;
        return Objects.equals(getName(), ((DFAState)o).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
