package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.prototype.ConcretePrototype;
import org.junit.jupiter.api.Test;

/**
 * 原型模式测试
 *
 * @author LZY
 * @version v1.0
 */
public class PrototypeTest {

    /**
     * 测试原型模式
     */
    @Test
    public void testPrototype() {
        ConcretePrototype concretePrototype = new ConcretePrototype("concretePrototype");
        ConcretePrototype clone = concretePrototype.copy();
        System.out.println("concretePrototype" + concretePrototype.getName());
        System.out.println("clone" + clone.getName());
    }
}
