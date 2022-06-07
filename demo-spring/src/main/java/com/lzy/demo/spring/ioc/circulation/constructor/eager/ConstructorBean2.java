package com.lzy.demo.spring.ioc.circulation.constructor.eager;

import org.springframework.stereotype.Component;

/**
 * 构建函数循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class ConstructorBean2 {
    private ConstructorBean1 constructorBean1;

    public ConstructorBean2(ConstructorBean1 constructorBean1) {
        this.constructorBean1 = constructorBean1;
    }
}
