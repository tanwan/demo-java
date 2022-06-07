package com.lzy.demo.spring.ioc.lazy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * {@code @Lazy}作用在类上,那么此类所有声明的Bean都将是@Lazy的
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
@Lazy
public class LazyConfiguration {

    /**
     * 由于类上面有@Lazy,因此这个bean将是lazy的
     *
     * @return the bean
     */
    @Bean
    public ConfigurationLazyBean lazyBean() {
        return new ConfigurationLazyBean("lazyBean");
    }

    /**
     * 这里添加了@Lazy(false),因此会重写这个类上的@Lazy,也就是这个bean是非Lazy的
     *
     * @return the bean
     */
    @Lazy(false)
    @Bean
    public ConfigurationLazyBean eagerBean() {
        return new ConfigurationLazyBean("eagerBean");
    }
}
