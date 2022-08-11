package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.strategy.Context;
import com.lzy.demo.design.pattern.strategy.Strategy1;
import com.lzy.demo.design.pattern.strategy.Strategy2;
import org.junit.jupiter.api.Test;

/**
 * 测试策略
 *
 * @author lzy
 * @version v1.0
 */
public class StrategyTest {

    /**
     * 测试策略
     */
    @Test
    public void testStrategy() {
        Context context = new Context(new Strategy1());
        context.executeStrategy();
        context = new Context(new Strategy2());
        context.executeStrategy();
    }
}
