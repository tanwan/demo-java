/*
 * Created by lzy on 2018/10/10 1:04 PM.
 */
package com.lzy.demo.spring.ioc.circulation;

import com.lzy.demo.spring.AbstractSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;

/**
 * 测试循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Slf4j
public class CirculationTest extends AbstractSpringTest {

    /**
     * 测试构造函数循环依赖,解决方案:1:使用@Lazy. 2:改成注入
     * 测试bean:ConstructorBean1,ConstructorBean2
     */
    @Test
    public void testConstructorCirculationTest() {
        //断言会抛出循环依赖的异常
        Assertions.assertThatExceptionOfType(UnsatisfiedDependencyException.class)
                .isThrownBy(() -> initApplicationContext("constructor.eager"));
    }

    /**
     * 测试构造函数循环依赖,使用@Lazy解决循环依赖
     * 测试bean:ConstructorLazyBean1,ConstructorLazyBean2
     */
    @Test
    public void testLazyConstructorCirculationTest() {
        //断言不会抛出任何异常
        Assertions.assertThatCode(() -> initApplicationContext("constructor.lazy"))
                .doesNotThrowAnyException();
    }

    /**
     * 测试注入循环依赖
     * 测试bean:SetterBean1,SetterBean2,Spring内部解决循环依赖
     */
    @Test
    public void testSetterCirculationTest() {
        //断言不会抛出任何异常
        Assertions.assertThatCode(() -> initApplicationContext("setter.raw"))
                .doesNotThrowAnyException();
    }

    /**
     * 测试注入循环依赖(异步增强)
     * 使用AsyncAnnotationBeanPostProcessor增强(在AbstractAutowireCapableBeanFactory#initializeBean进行增强)
     * 在获取早期引用中不会进行增强,所以会抛出异常
     * 测试bean:SetterBean1,SetterBean2
     */
    @Test
    public void testAsyncSetterCirculationTest() {
        //断言会抛出异常
        Assertions.assertThatExceptionOfType(BeanCurrentlyInCreationException.class)
                .isThrownBy(() -> initApplicationContext("setter.async"));
    }

    /**
     * 测试注入循环依赖(AOP增强)
     * 使用AnnotationAwareAspectJAutoProxyCreator增强(在AbstractAutowireCapableBeanFactory#getEarlyBeanReference进行增强)
     * 在获取早期引用中就进行了增强,所以不会报错
     * 测试bean:SetterBean1,SetterBean2
     */
    @Test
    public void testAopSetterCirculationTest() {
        //断言不会抛出任何异常
        Assertions.assertThatCode(() -> initApplicationContext("setter.aop"))
                .doesNotThrowAnyException();
    }
}
