package com.lzy.demo.spring.ioc.circulation.constructor.lazy;

import org.springframework.stereotype.Component;

/**
 * 构建函数循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class ConstructorLazyBean2 {
    private ConstructorLazyBean1 constructorLazyBean1;

    public ConstructorLazyBean2(ConstructorLazyBean1 constructorLazyBean1) {
        this.constructorLazyBean1 = constructorLazyBean1;
    }
}
