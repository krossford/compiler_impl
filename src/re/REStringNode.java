package re;

public class REStringNode extends RETreeNode {

    public String content = "";

    public REStringNode(String s) {
        content = s;
    }

    @Override
    public String toRE() {
        return content;
    }
}
