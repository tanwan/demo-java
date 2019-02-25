/*
 * Created by lzy on 2018/10/13 11:06 PM.
 */
package com.lzy.demo.spring.ioc.circulation.setter.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * The type Aop config.
 *
 * @author lzy
 * @version v1.0
 */
@EnableAspectJAutoProxy
@Aspect
@Component
public class AopConfig {

    /**
     * Before.
     */
    @Before("execution(public void com.lzy.demo.spring.ioc.circulation.setter.aop.AopSetterBean*.*())")
    public void before() {
        System.out.println("before");
    }
}
