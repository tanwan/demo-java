package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.bridge.ConcreteImplementor;
import com.lzy.demo.design.pattern.bridge.RefinedAbstraction;
import org.junit.jupiter.api.Test;

/**
 * 桥接模式测试
 *
 * @author LZY
 * @version v1.0
 */
public class BridgeTest {

    /**
     * 测试桥接模式
     */
    @Test
    public void testBridge() {
        RefinedAbstraction refinedAbstraction = new RefinedAbstraction(new ConcreteImplementor());
        refinedAbstraction.operation();
    }
}
