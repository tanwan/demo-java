package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.visitor.ConcreteElement1;
import com.lzy.demo.design.pattern.visitor.ConcreteElement2;
import com.lzy.demo.design.pattern.visitor.ConcreteVisitor;
import com.lzy.demo.design.pattern.visitor.Element;
import org.junit.jupiter.api.Test;

/**
 * 测试访问者
 *
 * @author lzy
 * @version v1.0
 */
public class VisitorTest {

    /**
     * 测试访问者
     */
    @Test
    public void testVisitor() {
        Element element = new ConcreteElement1();
        element.accept(new ConcreteVisitor());

        element = new ConcreteElement2();
        element.accept(new ConcreteVisitor());
    }
}
