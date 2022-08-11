package com.lzy.demo.design.pattern;


import com.lzy.demo.design.pattern.flyweight.FlyweightFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试享元
 *
 * @author lzy
 * @version v1.0
 */
public class FlyweightTest {

    /**
     * 测试享元
     */
    @Test
    public void testFlyweight() {
        assertEquals(FlyweightFactory.getFlyweight("flyweight1"), FlyweightFactory.getFlyweight("flyweight1"));
    }
}
