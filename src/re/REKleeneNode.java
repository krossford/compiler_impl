package re;

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
}
