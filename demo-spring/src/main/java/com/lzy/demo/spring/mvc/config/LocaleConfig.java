package com.lzy.demo.spring.mvc.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class LocaleConfig {

    /**
     * 定义LocaleContextResolver
     * bean名必需为localeResolver
     *
     * @return the locale context resolver
     * @see DispatcherServlet#initLocaleResolver(org.springframework.context.ApplicationContext)
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#localeResolver()
     */
    @Bean
    public LocaleContextResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("zone");
        cookieLocaleResolver.setCookieDomain("/");
        return cookieLocaleResolver;
    }
}
