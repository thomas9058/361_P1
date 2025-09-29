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
        private Map<String, Map<Character, String>> delta;
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
                delta.get(fromState).put(onSymb, toState);
            success= true;
        }
        return success;
        //@FIXIT, DELETE
        //References
        //https://howtodoinjava.com/java/collections/hashmap/java-nested-map/#:~:text=Creating%20Nested%20Map%20using%20Map,()%3B%20addressMap.
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA newDFA = new DFA(); 
        //Creates bew DFA that will have everything the same except the swapped transitions
        newDFA.state = new LinkedHashSet<>(this.state);
        newDFA.sigma = new LinkedHashSet<>(this.sigma);
        newDFA.finalState = new LinkedHashSet<>(this.finalState);
        newDFA.initial = this.initial;
        newDFA.delta = new LinkedHashMap<>();
        //Initialize the 5-tuple and right after enters a loop for delta
        for(String newState : this.delta.keySet()){ // Gets the keys for all the deltas aka their states
            Map<Character, String> mapDelta = this.delta.get(newState); //Creates a new map with the state that w egot
            Map<Character, String> swappedDelta = new LinkedHashMap<>(); //New Map for the swapped delta
            for(Map.Entry<Character, String> innerDelta : mapDelta.entrySet()){ //Enters loop for the inner delta
                
                char symbol = innerDelta.getKey(); //Gets the key aka the alphabet
                String stateToSwap = innerDelta.getValue(); //Gets the value aka the state
                
                if(symbol == symb1){ // if we have the symbol we got the same as the symb1
                    swappedDelta.put(symb2, stateToSwap); //then we put inside the swapped delta the inverse
                } else if(symb2 == symbol){ //otherwise we swap too since second symbol isthe same
                    swappedDelta.put(symb1, stateToSwap); 
                } else{ //if not, we just put it as is
                    swappedDelta.put(symbol, stateToSwap);
                }
            }
            newDFA.delta.put(newState, mapDelta);
        }

        return newDFA;
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
        boolean success = false;
        String newState = initial;
        for(int i = 0; i < s.length(); i++){ //Loop that will run the entire string
            char sigmaChar = s.charAt(i);
            Map<Character, String> trans = delta.get(newState); // Gets the transition related to the initial state
                                                                // then later, to any followed states

            if (sigma.contains(sigmaChar) != true || trans == null || trans.containsValue(sigmaChar) != true){
                //will return null if the character is null
                // if the transition itself at null, meaning that there is no transition related to that char
                // and finally, if the DFA does not accept the string
                success = false;
                break; // leaves loop
            }
            newState = trans.get(sigmaChar); //gets the next state
            success = true;
        }
        return success;
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
    
    @Override
    public String toString() {
        //New SB created to append all the values
        StringBuilder fullDFA = null;
        fullDFA.append("Q = { ");
        for (State states : state){ //loop so that it can go over every single state on the set
            fullDFA.append(states.toString() + " ");
        }
        fullDFA.append("}\n");
        fullDFA.append("Sigma = {");
        for (char sigmas : sigma){ //loop so it can go over every single character on the set
            fullDFA.append(sigmas + " ");
        }
        fullDFA.append("}\n");
        fullDFA.append("delta =\n");
        fullDFA.append( "   ");
        for (char sigmas : sigma){ //loop so it can go over every single chracter again, but now to build delta
            fullDFA.append("   " + sigmas);
        }
        for(Map.Entry<String, Map<Character, String>> deltas : delta.entrySet()){
            Map<Character, String> valueDelta = deltas.getValue();
            fullDFA.append("    " + deltas.getKey()); //put the outside/corner states
            for(Map.Entry<Character, String> innerDelta : valueDelta.entrySet()){
                fullDFA.append("    " + innerDelta.getKey()); // put the inside states
            }
            fullDFA.append("\n");
        }
        fullDFA.append("q0 = " + initial);
        fullDFA.append("\nF = { "); // loop to go over all final states
            for (State finals : finalState){
            fullDFA.append(finals.toString() + " ");
        }
        fullDFA.append("}\n");
        return fullDFA.toString();
    }
}
