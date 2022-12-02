package com.lzy.demo.spring.ioc.lazy.dependlazy;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 只有依赖加了@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Getter
public class DependLazyBean1 implements InitializingBean {

    @Lazy
    @Resource
    private DependLazyBean2 dependLazyBean2;

    public DependLazyBean1() {
        System.out.println("DependLazyBean1()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DependLazyBean1#afterPropertiesSet()");
    }
}
