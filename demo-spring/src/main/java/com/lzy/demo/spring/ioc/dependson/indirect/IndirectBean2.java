/*
 * Created by lzy on 2018/10/29 10:41 AM.
 */
package com.lzy.demo.spring.ioc.dependson.indirect;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version v1.0
 */
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
