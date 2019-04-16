package re;

import nfa.NFA;

import java.util.ArrayList;
import java.util.List;

/**
 * 顺序节点，将平级的节点连接起来
 * */
public class RESequenceNode extends RETreeNode {

    public List<RETreeNode> list = new ArrayList<>();

    @Override
    public String toRE() {
        StringBuilder sb = new StringBuilder();
        for (RETreeNode node : list) {
            sb.append(node.toRE());
        }
        return sb.toString();
    }

    @Override
    public NFA toNFA() {
        if (list == null || list.size() == 0) {
            return null;
        } else {
            NFA nfa = list.get(0).toNFA();
            for (int i = 1; i < list.size(); i++) {
                nfa = NFA.link(nfa, list.get(i).toNFA());
            }
            return nfa;
        }
    }
}
