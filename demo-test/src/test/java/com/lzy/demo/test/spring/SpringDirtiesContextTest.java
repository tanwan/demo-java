package com.lzy.demo.test.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AbstractDirtiesContextTestExecutionListener;

import jakarta.annotation.Resource;

/**
 * SpringBoot在程序退出之后,会使用SpringApplicationShutdownHook来进行ApplicationContext的关闭
 * 但是都在生育退出之后再进行关闭,有可能会有问题
 * 比如使用EmbeddedDatabase测试的时候,实例只会在所以测试退出后才会进行销毁,但是执行每次测试类的时候都会进行一次初始化,
 * 所以可能会报错,OauthAuthorizationServerTest就有这个问题
 * DirtiesContext可以指定ApplicationContext在何时重建
 *
 * @author lzy
 * @version v1.0
 * @see com.lzy.demo.oauth.authorization.server.OauthAuthorizationServerTest
 * @see org.springframework.boot.SpringApplicationShutdownHook#addRuntimeShutdownHook()
 * @see AbstractDirtiesContextTestExecutionListener#beforeOrAfterTestMethod
 */
public class SpringDirtiesContextTest {

    /**
     * 默认为TestInstance.Lifecycle.PER_METHOD
     * 执行每次测试都会创建实例
     */
    public static class PerMethodTest {
        @Resource
        private SpringBean springBean;

        /**
         * Test first.
         */
        @Test
        public void testFirst() {
            System.out.println("this:" + this + ",springBean:" + springBean);
        }

        /**
         * Test second.
         */
        @Test
        public void testSecond() {
            testFirst();
        }
    }

    /**
     * TestInstance.Lifecycle.PER_CLASS只会创建一次实例
     */
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public static class PerClassTest extends PerMethodTest {

    }

    /**
     * 没有DirtiesContext注解的测试
     * 本质上还是TestInstance.Lifecycle.PER_METHOD
     * 执行每次测试的时候都会创建测试类,但是spring容器只会启动一次
     * 所以这边的测试结果,两次的this是不同的实例,但是springBean是同一个
     */
    @SpringJUnitConfig(classes = SpringBean.class)
    public static class WithoutDirtiesContextTest extends PerMethodTest {

    }

    /**
     * 有DirtiesContext注解的测试
     * 本质上还是TestInstance.Lifecycle.PER_METHOD
     * 执行每次测试的时候都会创建测试类,但是spring容器只会启动一次
     * 所以这边的测试结果,两次的this是不同的实例
     * 因为这边使用了DirtiesContext,在每次方法执行后会进行一次销毁ApplicationContext
     * 所以这边的测试结果,两次的this是不同的实例,两次的springBean也不是同一个
     *
     * @see AbstractDirtiesContextTestExecutionListener#beforeOrAfterTestMethod
     */
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public static class WithDirtiesContextTest extends WithoutDirtiesContextTest {

    }
}
