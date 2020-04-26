/*
 * Created by lzy on 2019-03-29 00:11.
 */
package com.lzy.demo.aspectj;

import com.lzy.demo.aspectj.bean.SimpleAspectBean1;
import com.lzy.demo.aspectj.bean.SimpleAspectBean2;
import com.lzy.demo.aspectj.bean.Caller;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * aspectj测试,测试(test目录下)的不会进行AOP
 *
 * @author lzy
 * @version v1.0
 */
public class AspectJTest {


    /**
     * 测试call
     */
    @Test
    public void testCall() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).call();
    }


    /**
     * 测试execution
     */
    @Test
    public void testExecution() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).execution();
    }

    /**
     * 测试参数需要注解
     */
    @Test
    public void testParameterAnnotation() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).parameterAnnotation();
    }

    /**
     * 测试类型需要注解
     */
    @Test
    public void typeAnnotation() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).typeAnnotation();
    }


    /**
     * 测试within
     */
    @Test
    public void testWithin() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).within();
    }

    /**
     * 测试@within
     */
    @Test
    public void testAtWithin() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).atWithin();
    }

    /**
     * 测试this
     */
    @Test
    public void testThis() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).thiz();
    }

    /**
     * 测试this并获取参数
     */
    @Test
    public void testThisArgs() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).thisArgs();
    }

    /**
     * 测试target
     */
    @Test
    public void testTarget() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).target();
    }

    /**
     * 测试@target
     */
    @Test
    public void testAtTarget() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).atTarget();
    }

    /**
     * 测试target
     */
    @Test
    public void testTargetArgs() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).targetArgs();
    }

    /**
     * 测试args
     */
    @Test
    public void testArgs() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).args();
    }

    /**
     * 测试@args
     */
    @Test
    public void testAtArgs() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).atArgs();
    }

    /**
     * 测试@annotation
     */
    @Test
    public void testAtAnnotation() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).atAnnotation();
    }

    /**
     * 测试handler
     */
    @Test
    public void testHandler() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).handler();
    }

    /**
     * 测试aop顺序
     */
    @Test
    public void testOrder() {
        new Caller(Collections.singletonList(new SimpleAspectBean1())).order();
    }

    /**
     * 测试实例化模型
     */
    @Test
    public void testClause() {
        new Caller(Arrays.asList(new SimpleAspectBean1(), new SimpleAspectBean2())).clause();
    }
}
