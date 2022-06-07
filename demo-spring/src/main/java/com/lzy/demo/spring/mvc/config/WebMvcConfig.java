package com.lzy.demo.spring.mvc.config;

import com.lzy.demo.spring.mvc.interceptor.MvcInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * spring mvc 配置
 *
 * @author lzy
 * @version v1.0
 * @see DelegatingWebMvcConfiguration#setConfigurers(java.util.List)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /**
     * {@inheritDoc}
     * 从这里添加的拦截器只能拦截经过Controller的请求,无法拦截静态资源(比如:/index.html)
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MvcInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
    }

    /**
     * {@inheritDoc}
     * 路径的配置
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                //是否匹配无关的斜杠
                .setUseTrailingSlashMatch(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    /**
     * {@inheritDoc}
     * 添加静态资源handler
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //可以使用127.0.0.1:8080/resources/fileName访问在public-resources下的资源
        //在这里使用classpath也可以扫描到依赖包下的资源
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/public-resources/");
    }

    /**
     * {@inheritDoc}
     * 跨域资源共享配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    }
}
