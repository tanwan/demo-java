package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.responsibility.ConcreteHandler1;
import com.lzy.demo.design.pattern.responsibility.ConcreteHandler2;
import com.lzy.demo.design.pattern.responsibility.Handler;
import org.junit.jupiter.api.Test;

/**
 * 测试责任链
 *
 * @author lzy
 * @version v1.0
 */
public class ResponsibilityTest {


    /**
     * 测试责任链
     */
    @Test
    public void testResponsibility() {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        handler1.setNextHandler(handler2);
        handler1.handler();
    }
}
