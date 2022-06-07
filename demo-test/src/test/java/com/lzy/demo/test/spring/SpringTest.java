package com.lzy.demo.test.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

/**
 * spring测试
 *
 * @author lzy
 * @version v1.0
 */
public class SpringTest {

    /**
     * SpringExtension将spring和junit集成,已经在SpringJUnitConfig包含了
     * SpringJUnitConfig包含了ContextConfiguration,用来配置ApplicationContext
     */
    @SpringJUnitConfig(SpringBean.class)
    public static class SpringJUnitConfigTest {

        /**
         * SpringJUnitConfig注解的类同样也是spring bean,所以可以注入SpringBean
         */
        @Resource
        private SpringBean springBean;

        /**
         * 测试SpringJUnitConfig
         */
        @Test
        public void testSpringJUnitConfig() {
            System.out.println(springBean);
        }
    }

    /**
     * TestPropertySource用来添加properties配置, 直接指定properties的值
     * 注意TestPropertySource不要用来加载yml文件,否则它会将yml当成properties处理
     * ConfigDataApplicationContextInitializer的作用是可以获取springboot的配置
     */
    @SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class)
    @TestPropertySource(properties = "spring.config.additional-location=classpath:properties.yml")
    public static class PropertySourceTest {

        /**
         * 测试使用TestPropertySource添加配置
         *
         * @param key      the key
         * @param location the location
         * @param innerKey the inner key
         */
        @Test
        public void testPropertySource(@Value("${spring.config.additional-location}") String location,
                                       @Value("${key}") String key, @Value("${outer.inner.key}") String innerKey) {
            // TestPropertySource为spring.config.additional-location设置了值
            // 也就是为springboot添加了额外的配置文件
            Assertions.assertEquals("classpath:properties.yml", location);
            // ConfigDataApplicationContextInitializer的作用就是读取springboot的配置
            Assertions.assertEquals("value", key);
            Assertions.assertEquals("outer.inner.value", innerKey);
        }
    }

    /**
     * TestPropertySource加载yml配置
     * 会把yml文件当成properties处理
     */
    @ExtendWith(SpringExtension.class)
    @TestPropertySource(locations = "classpath:properties.yml")
    public static class PropertySourceLocationTest {

        /**
         * TestPropertySource加载yml配置会把yml文件当成properties处理
         *
         * @param key      the key
         * @param key2     the key2
         * @param innerKey the inner key
         */
        @Test
        public void testPropertySource(@Value("${key}") String key, @Value("${key2}") String key2, @Value("${outer.inner.key:#{null}}") String innerKey) {
            // 因为将yml当成properties处理
            // key会取成outer.inner.value(覆盖掉value)
            Assertions.assertEquals("outer.inner.value", key);
            // key2可以取到outer.inner.value2
            Assertions.assertEquals("outer.inner.value2", key2);
            // 而outer.inner.key则无法取到值
            Assertions.assertNull(innerKey);
        }
    }
}
