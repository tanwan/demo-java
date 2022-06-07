package com.lzy.demo.spring.mvc.config;

import com.lzy.demo.spring.mvc.interceptor.ResourceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * 使用此配置添加的拦截器,可以拦截controller和视图
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class MappedInterceptorConfig {

    /**
     * 使用这个添加的拦截器,只要路径匹配,全部都可以拦截到
     *
     * @return the mapped interceptor
     */
    @Bean
    public MappedInterceptor mappedInterceptor() {
        return new MappedInterceptor(new String[]{"/**/*.html"}, new ResourceInterceptor());
    }
}
