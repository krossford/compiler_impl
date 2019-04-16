package re;

import org.junit.Assert;
import org.junit.Test;

public class RETreeNodeTest {

    @Test
    public void test1() {

        String re = "abc";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);

        treeNode.toNFA();
    }

    @Test
    public void test2() {

        String re = "abc|bc";
        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test3() {

        String re = "abc|bc|qqq";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test4() {

        String re = "abc|bc|";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }

    @Test
    public void test5() {

        String re = "ab*";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
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

    @Test
    public void test11() {

        String re = "(ab)*d";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
    }
}
