package nfa;

import java.util.*;

public class NFAState {

    public Map<String, Set<NFAState>> transMap = new HashMap<>();

    public NFAState() {

    }

    public void addTrans(String input, NFAState toState) {
        Set<NFAState> stateSet = transMap.getOrDefault(input, new HashSet<>());
        stateSet.add(toState);
        transMap.put(input, stateSet);
    }
}
