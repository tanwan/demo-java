package com.lzy.demo.spring.ioc.lazy.alllazy;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 依赖和声明都加@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Getter
public class AllLazyBean1 implements InitializingBean {

    @Lazy
    @Resource
    private AllLazyBean2 allLazyBean2;

    public AllLazyBean1() {
        System.out.println("AllLazyBean1()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AllLazyBean1#afterPropertiesSet()");
    }
}
