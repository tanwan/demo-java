package com.lzy.demo.spring.ioc.lazy.declarelazy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * 只有声明加@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Lazy
@Component
public class DeclareLazyBean implements InitializingBean {

    public DeclareLazyBean() {
        System.out.println("DeclareLazyBean()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DeclareLazyBean#afterPropertiesSet()");
    }
}
