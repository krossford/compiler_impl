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

        Stack<Stack<RETreeNode>> stacks = new Stack<>();

        Stack<RETreeNode> currStack = new Stack<>();

        stacks.push(currStack);

        for (int i = 0; i < re.toCharArray().length; i++) {
            char c = re.charAt(i);
            if (c == '(') {
                // 遇见左括号，要创建一个新的栈出来存放字符
                Stack<RETreeNode> newStack = new Stack<>();
                stacks.push(newStack);
                currStack = newStack;
            } else if (c == ')') {
                RETreeNode seqNode = popStackToNode(currStack);
                stacks.pop();
                currStack = stacks.peek();
                if (!currStack.isEmpty() && currStack.peek() instanceof RESelectionNode) {
                    i--;
                    ((RESelectionNode) currStack.peek()).right = seqNode;
                } else {
                    currStack.push(seqNode);
                }
            } else if (c == '|') {
                RETreeNode seqNode = popStackToNode(currStack);
                RESelectionNode selectionNode = new RESelectionNode();
                selectionNode.left = seqNode;
                currStack.push(selectionNode);

                Stack<RETreeNode> newStack = new Stack<>();
                stacks.push(newStack);
                currStack = newStack;
            } else if (c == '*') {
                RETreeNode node = currStack.pop();
                REKleeneNode kleeneNode = new REKleeneNode();
                kleeneNode.op = node;
                currStack.push(kleeneNode);
            } else if (isLetter(c) || isNumber(c)) {
                currStack.push(new REStringNode(String.valueOf(c)));
            } else {
                System.out.println("Error, unknown character: " + c);
                return null;
            }
        }

        RETreeNode node = null;

        while (!currStack.isEmpty() || !stacks.isEmpty()) {
            node = popStackToNode(currStack);

            if (!stacks.isEmpty()) {
                stacks.pop();
            }

            if (stacks.isEmpty()) {
                break;
            }

            currStack = stacks.peek();

            if (!currStack.isEmpty() && currStack.peek() instanceof RESelectionNode) {
                ((RESelectionNode) currStack.peek()).right = node;
            } else {
                currStack.push(node);
            }
        }

        return node;
    }

    public static RETreeNode popStackToNode(Stack<RETreeNode> stack) {
        if (stack.size() == 1) {
            return stack.pop();
        } else {
            RESequenceNode node = new RESequenceNode();

            while (!stack.isEmpty()) {
                node.list.add(0, stack.pop());
            }
            return node;
        }
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    public abstract String toRE();

}
