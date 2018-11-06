/*
 * Created by lzy on 2018/11/5 1:07 PM.
 */
package com.lzy.demo.spring.ioc.beans.definition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Load bean definition config.
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class LoadBeanDefinitionConfig {


    /**
     * User at bean user at bean.
     *
     * @return the user at bean
     */
    @Bean
    public UserAtBean userAtBean() {
        return new UserAtBean();
    }
}
