package com.lzy.demo.spring.ioc.circulation.constructor.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 构建函数循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class ConstructorLazyBean1 {
    private ConstructorLazyBean2 constructorLazyBean2;

    public ConstructorLazyBean1(@Lazy ConstructorLazyBean2 constructorLazyBean2) {
        this.constructorLazyBean2 = constructorLazyBean2;
    }
}
