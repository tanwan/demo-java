package com.lzy.demo.spring.ioc.lazy.dependlazy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 只有依赖加了@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class DependLazyBean2 implements InitializingBean {

    public DependLazyBean2() {
        System.out.println("DependLazyBean2()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DependLazyBean2#afterPropertiesSet()");
    }
}
