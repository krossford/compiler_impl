package re;

import nfa.NFA;
import nfa.NFAState;

public class REStringNode extends RETreeNode {

    public char content;

    public REStringNode(char s) {
        content = s;
    }

    @Override
    public String toRE() {
        return String.valueOf(content);
    }

    @Override
    public NFA toNFA(boolean isAccept) {
        NFA nfa = new NFA();
        nfa.startState = new NFAState();
        nfa.endState = new NFAState();
        nfa.endState.isAccept = isAccept;
        nfa.startState.addTrans(String.valueOf(content), nfa.endState);
        return nfa;
    }
}
