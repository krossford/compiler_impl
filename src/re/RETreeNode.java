package re;

import com.sun.media.sound.RIFFInvalidDataException;
import nfa.NFA;
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
                // 遇见左括号，创建一个新栈就可以了
                Stack<RETreeNode> newStack = new Stack<>();
                stacks.push(newStack);
                currStack = newStack;
            } else if (c == ')') {
                // 遇见右括号

                // 先将当前栈的内容全部拼接在一起成一个节点
                RETreeNode seqNode = popStackToNode(currStack);

                // 调整 当前栈的指针
                stacks.pop();
                currStack = stacks.peek();

                // 判断一下当前栈（上个栈）的栈顶元素，是否是一个选择关系节点
                if (!currStack.isEmpty() && currStack.peek() instanceof RESelectionNode) {

                    // 如果是的话，那么回退下标，因为这次的退栈是为了处理选择节点的右边的部分，而不是与左括号对应的退栈
                    i--;

                    // 将之前拼接在一起的节点存到选择节点的右侧
                    ((RESelectionNode) currStack.peek()).right = seqNode;
                } else {

                    // 如果是个普通的节点，那么push上去就行了
                    currStack.push(seqNode);
                }
            } else if (c == '|') {

                // 遇到 选择 关系

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
                currStack.push(new REStringNode(c));
            } else {
                System.out.println("Error, unknown character: " + c);
                return null;
            }
        }

        RETreeNode node = null;

        while (!currStack.isEmpty() || !stacks.isEmpty()) {
            node = popStackToNode(currStack);

            if (!stacks.isEmpty()) stacks.pop();
            if (stacks.isEmpty()) break;

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

    public abstract NFA toNFA(boolean isAccept);

}
