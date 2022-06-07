package com.lzy.demo.spring.ioc.lazy.nolazy;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 依赖和声明都没有加@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Getter
public class NoLazyBean1 implements InitializingBean {

    @Resource
    private NoLazyBean2 noLazyBean2;

    public NoLazyBean1() {
        System.out.println("NoLazyBean1()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NoLazyBean1#afterPropertiesSet()");
    }
}
