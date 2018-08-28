/*
 * Created by lzy on 2018/8/22 11:43 AM.
 */
package com.lzy.demo.spring.ioc.lazy;

import com.lzy.demo.spring.AbstractSpringTest;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 测试注入和@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class LazyAutowiredTest extends AbstractSpringTest {
    /**
     * 测试注入和@Lazy
     */
    @Test
    public void testAutowiredLazy() {
        System.out.println("---------getBean()--------------");
        //Bean1注入了Bean2,使用@Lazy分为以下4种情况
        //1. 注入和声明都加@Lazy,那么只有在使用到bean2的情况下,才会对bean2进行加载
        //2. 注入加@Lazy,声明没加,那么在加载bean1的时候,并不会加载bean2,但是因为声明没有加@Lazy,所以当扫描到bean2时,bean2也会被立即初始化
        //3. 注入没加@Lazy,声明有加,虽然扫描到bean2时,bean2不会被立即加载,但是注入的时候相当于用到了bean2,因此加载bean1时,bean2也会被立即加载
        //4. 注入和声明都没加@Lazy,无论是扫描到bean1,还是bean2,bean2都会被立即加载
        Bean1 bean1 = context.getBean(Bean1.class);
        System.out.println("---------use bean--------------");
        System.out.println(bean1.bean2);
    }


    @Component
    private static class Bean1 {
        //如果这里没有@Lazy,那么即使在声明Bean2的时候加了@Lazy,当Bean1被加载时,bean2也会被加载
        //如果有@Lazy,那么在加载Bean1的时候,Bean2并不会被加载(可以解决注入的循环依赖)
        @Resource
        @Lazy
        private Bean2 bean2;

        Bean1() {
            System.out.println("bean1");
        }

    }

    /**
     * 在第一次使用的时候才会进行加载Bean2
     */
    @Lazy
    @Component
    private static class Bean2 {
        Bean2() {
            System.out.println("bean2");
        }
    }


}
