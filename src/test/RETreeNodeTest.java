package test;

import nfa.NFA;
import nfa.NFAState;
import org.junit.Assert;
import org.junit.Test;
import re.RETreeNode;

public class RETreeNodeTest {

    @Test
    public void test1() {

        String re = "abc";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);

        NFA nfa = NFA.removeEmptyTrans(treeNode.toNFA(true));
        System.out.println(nfa.toString());

        NFA nfa2 = new NFA();

        NFAState s0 = new NFAState();
        NFAState s1 = new NFAState();
        NFAState s2 = new NFAState();
        NFAState s3 = new NFAState();
        s0.addTrans("a", s1);
        s1.addTrans("b", s2);
        s2.addTrans("c", s3);
        s3.isAccept = true;

        nfa2.startState = s0;
        nfa2.endState = s3;

        System.out.println(nfa2.toString());

        Assert.assertEquals(nfa.toString(), nfa2.toString());

    }

    @Test
    public void test2() {

        String re = "abc|bc";
        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);

        NFA nfa = NFA.removeEmptyTrans(treeNode.toNFA(true));
        System.out.println(nfa.toString());
    }

    @Test
    public void test3() {

        String re = "abc|bc|qqq";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);

        NFA nfa = NFA.removeEmptyTrans(treeNode.toNFA(true));
        System.out.println(nfa.toString());
    }

    @Test
    public void test4() {

        String re = "abc|bc|";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);

        NFA nfa = NFA.removeEmptyTrans(treeNode.toNFA(true));
        System.out.println(nfa.toString());
    }

    @Test
    public void test5() {

        String re = "ab*";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);

        NFA nfa = NFA.removeEmptyTrans(treeNode.toNFA(true));
        System.out.println(nfa.toString());
    }

    @Test
    public void test6() {

        String re = "ab*c";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test7() {

        String re = "a(b|c)*d";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test8() {

        String re = "a(b|cqqq)*d";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test9() {

        String re = "a*(b|cqqq)*d";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test10() {

        String re = "(ab)*d";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }
}
