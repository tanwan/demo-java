/*
 * Created by lzy on 2018/8/22 5:32 PM.
 */
package com.lzy.demo.spring.ioc.lazy;

import com.lzy.demo.spring.AbstractSpringTest;
import com.lzy.demo.spring.ioc.lazy.configuration.ConfigurationLazyBean;
import org.junit.jupiter.api.Test;

/**
 * 配置类上@Lazy的测试类
 *
 * @author lzy
 * @version v1.0
 */
public class LazyConfigurationTest extends AbstractSpringTest {

    /**
     * 测试@Configuration上加@Lazy
     * ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod会为@Configuration的加载BeanDefinitions
     * 然后会为lazyBean的BeanDefinitions的lazyInit属性设置为true,eagerBean的BeanDefinitions的lazyInit属性设置为false
     * 在DefaultListableBeanFactory#preInstantiateSingletons()不会加载@Lazy的bean
     */
    @Test
    public void testLazyConfiguration() {
        //ConfigurationLazyBean():eagerBean
        //ConfigurationLazyBean#afterPropertiesSet():eagerBean
        //---------getBean()--------------
        //ConfigurationLazyBean():lazyBean
        //ConfigurationLazyBean#afterPropertiesSet():lazyBean
        initApplicationContext("configuration");
        System.out.println("---------getBean()--------------");
        context.getBean("lazyBean", ConfigurationLazyBean.class);
    }
}
