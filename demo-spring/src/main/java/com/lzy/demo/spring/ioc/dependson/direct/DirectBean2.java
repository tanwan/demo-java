package com.lzy.demo.spring.ioc.dependson.direct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DirectBean2 implements InitializingBean {
    public DirectBean2() {
        System.out.println("DirectBean2()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DirectBean2#afterPropertiesSet()");
    }
}
