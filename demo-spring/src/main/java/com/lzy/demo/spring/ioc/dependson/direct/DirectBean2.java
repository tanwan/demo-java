/*
 * Created by lzy on 2018/10/29 10:41 AM.
 */
package com.lzy.demo.spring.ioc.dependson.direct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version v1.0
 */
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
