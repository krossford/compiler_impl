package re;

import nfa.NFA;
import nfa.NFAState;

public class RESelectionNode extends RETreeNode{

    public RETreeNode left = null;
    public RETreeNode right = null;

    @Override
    public String toRE() {
        return left.toRE() + "|" + right.toRE();
    }

    @Override
    public NFA toNFA(boolean isAccept) {
        NFA nfa = new NFA();

        NFAState start = new NFAState();

        NFAState end = new NFAState();

        NFA leftNFA = left.toNFA(false);
        NFA rightNFA = right.toNFA(false);

        start.addTrans("", leftNFA.startState);
        start.addTrans("", rightNFA.startState);

        leftNFA.endState.addTrans("", end);
        rightNFA.endState.addTrans("", end);

        nfa.startState = start;
        nfa.endState = end;
        nfa.endState.isAccept = isAccept;

        return nfa;
    }
}
