package re;

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
}
