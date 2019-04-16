package nfa;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class NFA {

    public NFAState startState = null;

    public NFAState endState = null;

    public static NFA link(NFA nfa1, NFA nfa2) {
        nfa1.endState.addTrans("", nfa2.startState);
        nfa1.endState = nfa2.endState;
        return nfa1;
    }

    public static NFA removeEmptyTrans(NFA nfa) {
        removeEmptyTrans(nfa.startState);
        return nfa;
    }

    public static void removeEmptyTrans(NFAState state) {
        Set<NFAState> all = state.getAllTransState();

        all.stream().forEach(new Consumer<NFAState>() {
            @Override
            public void accept(NFAState nfaState) {
                if (nfaState.hasEmptySingleTrans()) {
                    nfaState.transMap = nfaState.getEmptySingleTransState().transMap;
                }
            }
        });

        all.stream().forEach(new Consumer<NFAState>() {
            @Override
            public void accept(NFAState nfaState) {
                removeEmptyTrans(nfaState);
            }
        });
    }
}
