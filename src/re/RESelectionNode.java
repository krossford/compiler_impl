package re;

public class RESelectionNode extends RETreeNode{

    public RETreeNode left = null;
    public RETreeNode right = null;

    @Override
    public String toRE() {
        return left.toRE() + "|" + right.toRE();
    }
}
