package com.lzy.demo.spring.ioc.circulation;

import com.lzy.demo.spring.ioc.circulation.constructor.eager.ConstructorBean1;
import com.lzy.demo.spring.ioc.circulation.constructor.eager.ConstructorBean2;
import com.lzy.demo.spring.ioc.circulation.constructor.lazy.ConstructorLazyBean1;
import com.lzy.demo.spring.ioc.circulation.constructor.lazy.ConstructorLazyBean2;
import com.lzy.demo.spring.ioc.circulation.setter.aop.AopConfig;
import com.lzy.demo.spring.ioc.circulation.setter.aop.AopSetterBean1;
import com.lzy.demo.spring.ioc.circulation.setter.aop.AopSetterBean2;
import com.lzy.demo.spring.ioc.circulation.setter.async.AsyncSetterBean1;
import com.lzy.demo.spring.ioc.circulation.setter.async.AsyncSetterBean2;
import com.lzy.demo.spring.ioc.circulation.setter.raw.SetterBean1;
import com.lzy.demo.spring.ioc.circulation.setter.raw.SetterBean2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * 测试循环依赖
 *
 * @author lzy
 * @version v1.0
 */
public class CirculationTest {

    /**
     * 测试构造函数循环依赖,解决方案:1:使用@Lazy. 2:改成注入
     * 测试bean:ConstructorBean1,ConstructorBean2
     */
    @Test
    public void testConstructorCirculation() {
        //断言会抛出循环依赖的异常
        assertThatExceptionOfType(UnsatisfiedDependencyException.class)
                .isThrownBy(() -> new AnnotationConfigApplicationContext(ConstructorBean1.class, ConstructorBean2.class));
    }


    /**
     * 测试构造函数循环依赖,使用@Lazy解决循环依赖
     * 测试bean:ConstructorLazyBean1,ConstructorLazyBean2
     */
    @Test
    public void testLazyConstructorCirculation() {
        //断言不会抛出任何异常
        assertThatCode(() -> new AnnotationConfigApplicationContext(ConstructorLazyBean1.class, ConstructorLazyBean2.class))
                .doesNotThrowAnyException();
    }


    /**
     * 测试注入循环依赖
     * 测试bean:SetterBean1,SetterBean2,Spring内部解决循环依赖
     */
    @Test
    public void testSetterCirculationTest() {
        //断言不会抛出任何异常
        assertThatCode(() -> new AnnotationConfigApplicationContext(SetterBean1.class, SetterBean2.class))
                .doesNotThrowAnyException();
    }

    /**
     * 测试注入循环依赖(异步增强,@EnableAsync使用jdk代理)
     * 使用AsyncAnnotationBeanPostProcessor增强(在AbstractAutowireCapableBeanFactory#initializeBean进行增强)
     * 在获取早期引用中不会进行增强,所以会抛出异常
     * 测试bean:SetterBean1,SetterBean2
     */
    @Test
    public void testAsyncSetterCirculationTest() {
        //断言会抛出异常
        assertThatExceptionOfType(BeanCurrentlyInCreationException.class)
                .isThrownBy(() -> new AnnotationConfigApplicationContext(AsyncSetterBean1.class, AsyncSetterBean2.class));
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
        assertThatCode(() -> new AnnotationConfigApplicationContext(AopConfig.class, AopSetterBean1.class, AopSetterBean2.class))
                .doesNotThrowAnyException();
    }
}
