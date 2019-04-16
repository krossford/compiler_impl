package nfa;

public class NFA {

    public NFAState startState = null;

    public NFAState endState = null;

    public static NFA link(NFA nfa1, NFA nfa2) {
        nfa1.endState.addTrans("", nfa2.startState);
        nfa1.endState = nfa2.endState;
        return nfa1;
    }
}
