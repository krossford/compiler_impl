package re;

import org.junit.Assert;
import org.junit.Test;

public class RETreeNodeTest {

    @Test
    public void test1() {

        String re = "abc";

        RETreeNode treeNode = RETreeNode.convert(re);
        Assert.assertEquals(treeNode.toRE(), re);
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
}
