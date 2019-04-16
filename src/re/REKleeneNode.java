package re;

import nfa.NFA;
import nfa.NFAState;

public class REKleeneNode extends RETreeNode {

    public RETreeNode op = null;

    @Override
    public String toRE() {
        String s = op.toRE();
        if (s.length() <= 1) {
            return s + "*";
        } else {
            return "(" + op.toRE() + ")*";
        }
    }

    @Override
    public NFA toNFA() {
        NFA nfa = new NFA();

        NFAState start = new NFAState();

        NFAState end = new NFAState();

        NFA opNFA = op.toNFA();

        opNFA.endState.addTrans("", opNFA.startState);
        opNFA.endState.addTrans("", end);

        start.addTrans("", opNFA.startState);
        start.addTrans("", end);

        nfa.startState = start;
        nfa.endState = end;

        return nfa;
    }
}
