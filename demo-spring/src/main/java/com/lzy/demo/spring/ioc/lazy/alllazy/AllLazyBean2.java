package com.lzy.demo.spring.ioc.lazy.alllazy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 依赖和声明都加@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Lazy
public class AllLazyBean2 implements InitializingBean {

    public AllLazyBean2() {
        System.out.println("AllLazyBean2()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AllLazyBean2#afterPropertiesSet()");
    }
}
