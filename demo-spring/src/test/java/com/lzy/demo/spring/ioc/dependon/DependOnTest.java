/*
 * Created by lzy on 2018/8/20 7:36 PM.
 */
package com.lzy.demo.spring.ioc.dependon;

import com.lzy.demo.spring.AbstractSpringTest;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 测试dependOn
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class DependOnTest extends AbstractSpringTest {
    /**
     * 测试@DependsOn
     */
    @Test
    public void testDependOn() {
    }

    /**
     * 如果一个bean1直接依赖bean2(通过注入),那么bean2需要在bean1前加载,
     * 如果bean1不直接依赖bean2,又需要bean2在bean1前加载,那么就需要使用@DependsOn
     * 比如事件发布者和事件监听者,监听者需要在发布者之前加载,才不会错过事件
     */
    @Component("bean1")
    @DependsOn("bean2")
    private static class Bean1 {
        //如果有直接依赖的话,那么bean2会bean1前加载
        //如果没有直接依赖的话,使用@DependsOn也会让bean2在bean1前加载
        //@Resource
        private Bean2 bean2;

        Bean1() {
            System.out.println("bean1");
        }
    }

    @Component("bean2")
    private static class Bean2 {
        Bean2() {
            System.out.println("bean2");
        }
    }
}
