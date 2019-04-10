package re;

import com.sun.media.sound.RIFFInvalidDataException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public abstract class RETreeNode {

    /**
     * 将正则表达式转换成语法树的结构
     *
     * example1:
     *
     *     re: abc
     *
     *     tree: abc
     *
     * example2:
     *
     *     re: abc|bc
     *
     *     tree:      |
     *              /  \
     *            abc   bc
     *
     * */
    public static RETreeNode convert(String re) {

        Stack<Stack<Character>> stacks = new Stack<>();

        Stack<Character> currStack = new Stack<>();

        stacks.push(currStack);

        RETreeNode root = null;
        RETreeNode currNode = null;

        for (Character c : re.toCharArray()) {
            if (c == '(') {
                Stack<Character> newStack = new Stack<>();
                stacks.push(newStack);
                currStack = newStack;
            } else if (c == ')') {

            } else if (c == '|') {
                String s = popStackToString(currStack);
                if (currNode == null) {
                    RESelectionNode selectionNode = new RESelectionNode();
                    selectionNode.left = new REStringNode(s);
                    currNode = selectionNode;
                    root = currNode;
                } else {
                    RESelectionNode selectionNode = new RESelectionNode();
                    selectionNode.left = new REStringNode(s);
                    if (currNode instanceof RESelectionNode) {
                        ((RESelectionNode) currNode).right = selectionNode;
                        currNode = selectionNode;
                    }
                }
            } else if (c == '*') {

            } else if (isLetter(c) || isNumber(c)) {
                currStack.push(c);
            } else {
                System.out.println("Error, unknown character: " + c);
                return null;
            }
        }

        if (currNode != null) {
            if (currNode instanceof RESelectionNode) {
                ((RESelectionNode) currNode).right = new REStringNode(popStackToString(currStack));
            }
        } else {
            root = currNode = new REStringNode(popStackToString(currStack));

        }

        return root;
    }

    public static String popStackToString(Stack<Character> stack) {
        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }

        return sb.toString();
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    public abstract String toRE();

}
