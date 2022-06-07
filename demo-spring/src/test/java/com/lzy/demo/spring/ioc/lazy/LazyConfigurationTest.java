package com.lzy.demo.spring.ioc.lazy;

import com.lzy.demo.spring.ioc.lazy.configuration.ConfigurationLazyBean;
import com.lzy.demo.spring.ioc.lazy.configuration.LazyConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 配置类上@Lazy的测试类
 *
 * @author lzy
 * @version v1.0
 */
@SpringJUnitConfig(LazyConfiguration.class)
public class LazyConfigurationTest {

    /**
     * 测试@Configuration上加@Lazy
     * ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod会为@Configuration的加载BeanDefinitions
     * 然后会为lazyBean的BeanDefinitions的lazyInit属性设置为true,eagerBean的BeanDefinitions的lazyInit属性设置为false
     * 在DefaultListableBeanFactory#preInstantiateSingletons()不会加载@Lazy的bean
     *
     * @param context context
     */
    @Test
    public void testLazyConfiguration(@Autowired ApplicationContext context) {
        //ConfigurationLazyBean():eagerBean
        //ConfigurationLazyBean#afterPropertiesSet():eagerBean
        //---------getBean()--------------
        //ConfigurationLazyBean():lazyBean
        //ConfigurationLazyBean#afterPropertiesSet():lazyBean
        System.out.println("---------getBean()--------------");
        context.getBean("lazyBean", ConfigurationLazyBean.class);
    }
}
