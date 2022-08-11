package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.composite.Composite;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 测试组合模式
 *
 * @author lzy
 * @version v1.0
 */
public class CompositeTest {

    /**
     * 测试组合模式
     */
    @Test
    public void testComposite() {
        Composite composite1 = new Composite("composite1");
        Composite composite2 = new Composite("composite2");
        Composite composite3 = new Composite("composite3", Arrays.asList(composite1, composite2));
        System.out.println(composite1);
        System.out.println(composite2);
        System.out.println(composite3);
    }
}
