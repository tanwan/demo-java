package com.lzy.demo.spring.ioc.dependson.direct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DirectBean1直接依赖DirectBean2
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class DirectBean1 implements InitializingBean {

    @Resource
    private DirectBean2 directBean2;

    public DirectBean1() {
        System.out.println("DirectBean1()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DirectBean1#afterPropertiesSet()");
    }
}
