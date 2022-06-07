package com.lzy.demo.spring.ioc.circulation.setter.raw;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注入循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SetterBean2 {

    @Resource
    private SetterBean1 setterBean1;
}
