/*
 * Created by lzy on 2018/8/6 9:10 PM.
 */
package com.lzy.demo.spring;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试抽象类
 *
 * @author lzy
 * @version v1.0
 */
public abstract class AbstractSpringTest {
    protected List<String> packages = new ArrayList<>();
    protected AnnotationConfigApplicationContext context;

    /**
     * 扫包
     */
    @Before
    public void before() {
        packages.add(getClass().getPackage().getName());
        context = new AnnotationConfigApplicationContext(packages.toArray(new String[0]));
        context.start();
        context.stop();
    }

    @After
    public void after(){
        context.stop();
        context.close();
    }
}
