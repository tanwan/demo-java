package com.lzy.demo.spring.ioc.lazy.configuration;

import org.springframework.beans.factory.InitializingBean;

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
