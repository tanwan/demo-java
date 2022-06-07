package com.lzy.demo.spring.ioc.dependson.indirect;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class IndirectBean2 implements InitializingBean {

    public IndirectBean2() {
        System.out.println("IndirectBean2()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("IndirectBean2#afterPropertiesSet()");
    }
}
