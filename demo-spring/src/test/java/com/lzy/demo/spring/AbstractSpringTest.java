/*
 * Created by lzy on 2018/8/6 9:10 PM.
 */
package com.lzy.demo.spring;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试抽象类
 *
 * @author lzy
 * @version v1.0
 */
public abstract class AbstractSpringTest {
    protected AnnotationConfigApplicationContext context;
    /**
     * 扫包
     */
    @Before
    public void start() {
        context = new AnnotationConfigApplicationContext(getClass());
        context.start();
    }

    /**
     * end
     */
    @After
    public void end() {
        context.stop();
        context.close();
    }
}
