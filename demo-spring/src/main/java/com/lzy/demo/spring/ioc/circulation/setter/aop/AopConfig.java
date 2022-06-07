package com.lzy.demo.spring.ioc.circulation.setter.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Aspect
@Component
public class AopConfig {

    @Before("execution(public void com.lzy.demo.spring.ioc.circulation.setter.aop.AopSetterBean*.*())")
    public void before() {
        System.out.println("before");
    }
}
