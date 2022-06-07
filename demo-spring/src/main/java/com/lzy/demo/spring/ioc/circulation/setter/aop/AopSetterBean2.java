package com.lzy.demo.spring.ioc.circulation.setter.aop;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * AOP注入循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class AopSetterBean2 {
    @Resource
    private AopSetterBean1 aopSetterBean1;

    /**
     * Aop method.
     */
    public void aopMethod() {
        System.out.println("AopSetterBean1#aopMethod");
    }
}
