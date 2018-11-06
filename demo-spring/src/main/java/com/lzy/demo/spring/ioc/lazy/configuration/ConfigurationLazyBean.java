/*
 * Created by lzy on 2018/10/29 5:00 PM.
 */
package com.lzy.demo.spring.ioc.lazy.configuration;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author lzy
 * @version v1.0
 */
public class ConfigurationLazyBean implements InitializingBean {
    private String beanName;

    public ConfigurationLazyBean(String beanName) {
        System.out.println("ConfigurationLazyBean():" + beanName);
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ConfigurationLazyBean#afterPropertiesSet():" + beanName);
    }
}
