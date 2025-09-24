package fa.dfa;

import fa.State;
import fa.dfa.DFAInterface;

import com.sun.net.httpserver.Authenticator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
//@fixit remember to do coments and inline coments
public class DFA implements DFAInterface{
    
        private Set<State> state;
        private Map<String, Map<String, Character>> delta;
        private Set<Character> sigma;
        private Set<State> finalState;
        private String initial;

    public DFA(){
        this.state = new LinkedHashSet<>();
        this.delta = new LinkedHashMap<>();
        this.sigma = new LinkedHashSet<>();
        this.finalState = new LinkedHashSet<>();
        this.initial = null;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        boolean success = false;
        if(getState(fromState) != null && getState(toState) != null
            && sigma.contains(onSymb)){
                delta.putIfAbsent(fromState, new LinkedHashMap<>());
                delta.get(fromState).put(toState, onSymb);
            success= true;
        }
        return success;
        //@FIXIT, DELETE
        //References
        //https://howtodoinjava.com/java/collections/hashmap/java-nested-map/#:~:text=Creating%20Nested%20Map%20using%20Map,()%3B%20addressMap.
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addState(String name) {
        boolean success = false;
        if(getState(name) == null){
            State newState = new State(name);
            success = state.add(newState);
        }
        return success;
    }

    @Override
    public boolean setFinal(String name) {
        Boolean success = false;
        if(getState(name) != null){
            success = finalState.add(new State(name));
        }
        return success;
    }

    @Override
    public boolean setStart(String name) {
        Boolean success = false;
        if(getState(name) != null){
            initial = name;
            success = true;
        }
        return success;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        State newState = null;
        if(state.contains(name)){
        newState = new State(name);
        }
        return newState;
    }

    @Override
    public boolean isFinal(String name) {
        State isFinal = getState(name);
        return isFinal != null;
    }

    @Override
    public boolean isStart(String name) {
        return name.equals(initial);
    }
    
    @Override //@FIXIT override
    public String toString() {
        StringBuilder fullDFA = null;
        fullDFA.append("Q = { ");
        for (State states : state){
            fullDFA.append(states.toString() + " ");
        }
        fullDFA.append("}\n");
        fullDFA.append("Sigma = {");
        for (char sigmas : sigma){
            fullDFA.append(sigmas + " ");
        }
        fullDFA.append("}\n");
        fullDFA.append("delta =\n");
        fullDFA.append( "   ");
        for (char sigmas : sigma){
            fullDFA.append("   " + sigmas);
        }
        for(Map.Entry<String, Map<String, Character>> deltas : delta.entrySet()){
            Map<String, Character> valueDelta = deltas.getValue();
            fullDFA.append("    " + deltas.getKey());
            for(Map.Entry<String, Character> innerDelta : valueDelta.entrySet()){
                fullDFA.append("    " + innerDelta.getKey());
            }
            fullDFA.append("\n");
        }
        fullDFA.append("q0 = " + initial);
        fullDFA.append("\nF = { ");
            for (State finals : finalState){
            fullDFA.append(finals.toString() + " ");
        }
        fullDFA.append("}\n");
        return fullDFA.toString();
    }
}
