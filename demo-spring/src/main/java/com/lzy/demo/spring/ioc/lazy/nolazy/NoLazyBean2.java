package com.lzy.demo.spring.ioc.lazy.nolazy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 依赖和声明都没有加@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class NoLazyBean2 implements InitializingBean {

    public NoLazyBean2() {
        System.out.println("NoLazyBean2()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NoLazyBean2#afterPropertiesSet()");
    }
}
