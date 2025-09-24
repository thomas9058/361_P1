
import com.sun.net.httpserver.Authenticator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fa.dfa.DFAInterface;

public class DFA implements DFAInterface{
    
        private Set<State> state;
        private Map<String, Map<String, Character>> delta;
        private Set<Character> sigma;
        private Set<State> finalState;
        private String initial;

    public DFA(){
        this.state = new HashSet<>();
        this.delta = new HashMap<>();
        this.sigma = new HashSet<>();
        this.finalState = new HashSet<>();
        this.initial = null;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        boolean success = false;
        if(getState(fromState) != null && getState(toState) != null
            && sigma.contains(onSymb)){
                delta.putIfAbsent(fromState, new HashMap<>());
                delta.get(fromState).put(toState, onSymb);
                delta.put(fromState, innerMap);
            success= true;
        }
        return success;

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
            state.add(newState);
            success = true;
        }
        return success;
    }

    @Override
    public boolean setFinal(String name) {
        Boolean success = false;
        if(getState(name) != null){
            finalState.add(name);
            success = true;
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

    @Override //@FIXIT override
    public boolean isStart(String name) {
        return name.equals(initial);
    }
    
        @Override //@FIXIT override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
