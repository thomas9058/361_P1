
import java.util.HashSet;
import java.util.Set;

import fa.dfa.DFAInterface;

public class DFA implements DFAInterface {

    
        private Set<State> state;
        private String delta;
        private Set<Character> sigma;
        private Set<State> final;
        private String initial;

    public DFA(){
        this.state = new HashSet<>();
        this.delta = initial;
        this.sigma = new HashSet<>();
        this.final = new HashSet<>();
        this.initial = null;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addState(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addState'");
    }

    @Override
    public boolean setFinal(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFinal'");
    }

    @Override
    public boolean setStart(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStart'");
    }

    @Override
    public void addSigma(char symbol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSigma'");
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
