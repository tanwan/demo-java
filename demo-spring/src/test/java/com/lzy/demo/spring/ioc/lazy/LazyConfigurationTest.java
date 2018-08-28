/*
 * Created by lzy on 2018/8/22 5:32 PM.
 */
package com.lzy.demo.spring.ioc.lazy;

import com.lzy.demo.spring.AbstractSpringTest;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 配置类上@Lazy的测试类
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class LazyConfigurationTest extends AbstractSpringTest {

    /**
     * 测试@Configuration上加@Lazy
     */
    @Test
    public void testLazyConfiguration() {
        System.out.println("---------getBean()--------------");
        context.getBean("lazyConfigEagerBean", Bean.class);
        //首次使用这个bean的时候才会进行加载
        context.getBean("lazyConfigLazyBean", Bean.class);
    }


    private static class Bean {
        Bean(String name) {
            System.out.println(name);
        }
    }

    /**
     * 加在@Configuration上的@Lazy会对该类所有的@Bean生效
     */
    @Lazy
    @Configuration
    public static class LazyConfiguration {

        /**
         * 由于@Configuration上有@Lazy,因此这个bean会在使用的时候再进行加载
         *
         * @return the bean
         */
        @org.springframework.context.annotation.Bean
        public Bean lazyConfigLazyBean() {
            return new Bean("lazyBean");
        }

        /**
         * 因为@Bean上有@Lazy,所以会重写@Configuration上的@Lazy,因此这个bean会在ApplicationContext启动的时候加载
         *
         * @return the bean
         */
        @Lazy(value = false)
        @org.springframework.context.annotation.Bean
        public Bean lazyConfigEagerBean() {
            return new Bean("eagerBean");
        }

    }
}
