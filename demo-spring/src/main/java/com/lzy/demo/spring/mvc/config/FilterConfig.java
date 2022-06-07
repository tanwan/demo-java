package com.lzy.demo.spring.mvc.config;

import com.lzy.demo.spring.mvc.filter.MvcFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置过滤器
 * 使用ServletRegistrationBean可以配置servlet
 *
 * @author lzy
 * @version v1.0
 * @see ServletContextInit
 */
public class FilterConfig {

    /**
     * 使用FilterRegistrationBean注册过滤器
     */
    @Configuration
    public static class FilterRegistrationBeanConfig {
        /**
         * 配置过滤器
         *
         * @return the filter registration bean
         */
        @Bean
        public FilterRegistrationBean filterRegistrationBean() {
            FilterRegistrationBean<MvcFilter> filterRegistrationBean = new FilterRegistrationBean<>(new MvcFilter());
            filterRegistrationBean.setName("mvcFilter");
            Map<String, String> params = new HashMap<>();
            params.put("param", "value");
            filterRegistrationBean.setInitParameters(params);
            //添加过滤规则. /*表示拦截全部,*.html表示只拦截html
            filterRegistrationBean.addUrlPatterns("/*");
            return filterRegistrationBean;
        }
    }


    /**
     * 使用ServletContextInit注册过滤器
     */
    @Configuration
    public static class ServletContextInit implements ServletContextInitializer {

        /**
         * {@inheritDoc}
         *
         * @see ServletWebServerApplicationContext#selfInitialize(javax.servlet.ServletContext)
         */
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            EnumSet<DispatcherType> dispatcherTypes = EnumSet
                    .of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST);
            //isMatchAfter表示新添加的filter是在已经添加的filter之前还是之后
            servletContext.addFilter("mvcFilter", new MvcFilter()).addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        }
    }


}
