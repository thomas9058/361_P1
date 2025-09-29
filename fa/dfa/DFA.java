package fa.dfa;

import fa.State;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Deterministic Finite Automata (DFA)
 * 
 * @author Dalton Bilau Goncalves, Thomas Pengelly
 */
public class DFA implements DFAInterface{

    // Alphabet
    private final LinkedHashSet<Character> sigma = new LinkedHashSet<>();

    // States
    private final LinkedHashSet<DFAState> states = new LinkedHashSet<>();

    // Name
    private final Map<String, DFAState> byName = new LinkedHashMap<>();

    // Delta
    private final LinkedHashMap<DFAState, LinkedHashMap<Character, DFAState>> delta = new LinkedHashMap<>();

    // q0
    private DFAState start = null;

    // final state(s)
    private final LinkedHashSet<DFAState> finals = new LinkedHashSet<>();

    /**
     * adds a transition delta(fromState, onSymb) = toState
     * 
     * @param fromState source state name
     * @param toState   destination state name
     * @param onSymb    input symbol
     * @return true if transition works; false if state dne or onSymb not in alphabet
     */
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        DFAState sFrom = byName.get(fromState);
        DFAState sTo = byName.get(toState);
        if (sFrom == null || sTo == null) return false;
        if (!sigma.contains(onSymb)) return false;

        // update table
        delta.get(sFrom).put(onSymb, sTo);
        sFrom.put(onSymb, sTo);
        return true;
    }

    /**
     * returns a new, identical DFA with every transition label symb1 is swapped with symb2
     * 
     * @param symb1 the first symbol to swap
     * @param symb2 the second symbol to swap
     * @return a new DFA whose transition labels are swapped
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        DFA copy = new DFA();

        // copy alphabet
        for (char c : this.sigma) copy.addSigma(c);

        // copy states
        for (DFAState s : this.states) copy.addState(s.getName());

        // copy q0 and F
        if (this.start != null) copy.setStart(this.start.getName());
        for (DFAState f : this.finals) copy.setFinal(f.getName());

        // recreate delta with swapped symbols
        for (DFAState from : this.states) {
            for (Map.Entry<Character, DFAState> e : this.delta.get(from).entrySet()) {
                char original = e.getKey();
                char outSymb = (original == symb1) ? symb2
                            : (original == symb2) ? symb1
                            : original;
                copy.addTransition(from.getName(), e.getValue().getName(), outSymb);
            }
        }
        return copy;
    }

    /**
     * adds a state to Q in insertion order
     * 
     * @param name state label
     * @return true if added; false if name is null or present
     */
    @Override
    public boolean addState(String name) {
        if (name == null || byName.containsKey(name)) return false;
        DFAState s = new DFAState(name);
        states.add(s);
        byName.put(name, s);
        delta.put(s, new LinkedHashMap<>()); // empty row for delta
        return true;
    }

    /**
     * marks a state as final
     * 
     * @param name state label
     * @return true if state exists and was added to F; otherwise false
     */
    @Override
    public boolean setFinal(String name) {
        DFAState s = byName.get(name);
        if (s == null) return false;
        return finals.add(s);
    }

    /**
     * sets the start state q0
     * 
     * @param name state label
     * @return true if state exists adn was set as start; otherwise false
     */
    @Override
    public boolean setStart(String name) {
        DFAState s = byName.get(name);
        if (s == null) return false;
        start = s;
        return true;
    }

    /**
     * adds a symbol to alphabet
     * 
     * @param symbol the symbol to add
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    /**
     * simulates the DFA on given input
     * 
     * @param input the input string
     * @return true iff the DFA accepts the string
     */
    @Override
    public boolean accepts(String input) {
        if (start == null) return false;
        if (input == null) input = ""; // treat null as epsilon

        DFAState cur = start;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (!sigma.contains(c)) return false; // reject symbol not in alphabet

            Map<Character, DFAState> row = delta.get(cur); // lookup delta(cur, c)
            if (row == null) return false; // should not happen
            DFAState next = row.get(c);
            if (next == null) return false; // missing transition
            cur = next;
        }
        return finals.contains(cur);
    }

    /**
     * @return alphabet 
     */
    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    /**
     * returns the state object with this name
     * 
     * @param name state label
     * @return the state (DFAState)
     */
    @Override
    public State getState(String name) {
        return byName.get(name);
    }

    /**
     * @param name state label
     * @return true iff the state exists and is in F
     */
    @Override
    public boolean isFinal(String name) {
        DFAState s = byName.get(name);
        return s != null && finals.contains(s);
    }

    /**
     * @param name state label
     * @return true iff the state exists and equals q0
     */
    @Override
    public boolean isStart(String name) {
        DFAState s = byName.get(name);
        return s != null && s.equals(start);
    }
    
    /**
     * returns a readable representation of the DFA matchin the given format
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // states
        sb.append("Q = { ");
        for (DFAState s : states) sb.append(s.getName()).append(' ');
        sb.append("}\n");

        // sigma
        sb.append("Sigma = { ");
        for (char c : sigma) sb.append(c).append(' ');
        sb.append("}\n");

        // delta header
        sb.append("delta =\n");
        sb.append("   ");
        for (char c : sigma) sb.append(c).append(' ');
        sb.append('\n');

        // delta rows
        for (DFAState row : states) {
            sb.append(row.getName()).append(' ');
            for (char c : sigma) {
                DFAState dest = delta.get(row).get(c);
                sb.append(' ').append(dest == null ? "-" : dest.getName());
            }
            sb.append('\n');
        }

        // q0
        sb.append("q0 = ").append(start == null ? "" : start.getName()).append('\n');

        // F
        sb.append("F = { ");
        for (DFAState f : finals) sb.append(f.getName()).append(' ');
        sb.append("}\n");

        return sb.toString();
    }
}
