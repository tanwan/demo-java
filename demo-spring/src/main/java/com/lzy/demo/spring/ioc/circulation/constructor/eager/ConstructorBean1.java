package com.lzy.demo.spring.ioc.circulation.constructor.eager;
import org.springframework.stereotype.Component;

/**
 * 构建函数循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class ConstructorBean1 {
    private ConstructorBean2 constructorBean2;

    public ConstructorBean1(ConstructorBean2 constructorBean2) {
        this.constructorBean2 = constructorBean2;
    }
}
