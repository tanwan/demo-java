package com.lzy.demo.spring.ioc.circulation.setter.aop;

import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * AOP注入循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class AopSetterBean1 {
    @Resource
    private AopSetterBean2 aopSetterBean2;


    /**
     * Aop method.
     */
    public void aopMethod() {
        System.out.println("AopSetterBean1#aopMethod");
    }
}
