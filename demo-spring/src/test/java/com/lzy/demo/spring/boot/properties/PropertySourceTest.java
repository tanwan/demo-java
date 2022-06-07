package com.lzy.demo.spring.boot.properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * {@code @PropertySource}测试
 * 由于spring 测试并没有注册ConfigurationPropertiesBindingPostProcessor,因此需要使用@EnableConfigurationProperties
 *
 * @author lzy
 * @version v1.0
 */
@EnableConfigurationProperties(AtConfigurationProperties.class)
public class PropertySourceTest {

    /**
     * 没有使用Factory,只能导入property文件,yml文件的话,只能使用configuration.properties.key: value
     */
    @SpringJUnitConfig(classes = {PropertySourceConfig.class})
    public static class WithoutFactory extends PropertySourceTest {
        /**
         * 测试@PropertySource
         *
         * @param atConfigurationProperties the at configuration properties
         * @param key                       the key
         */
        @Test
        public void testPropertySource(@Autowired AtConfigurationProperties atConfigurationProperties, @Value("${key}") String key) {
            Assertions.assertThat(key).isEqualTo("value");
            System.out.println(atConfigurationProperties);
            Assertions.assertThat(atConfigurationProperties)
                    .hasFieldOrPropertyWithValue("integer", 1)
                    .hasFieldOrPropertyWithValue("email", "99156629@qq.com")
                    .hasFieldOrPropertyWithValue("actualValue", "expectValue");
        }
    }

    /**
     * 使用Factory,可以导入yml文件
     */
    @SpringJUnitConfig(classes = {PropertySourceWithFactoryConfig.class})
    public static class WithFactory extends PropertySourceTest {
        /**
         * 测试@PropertySource使用factory
         *
         * @param atConfigurationProperties atConfigurationProperties
         */
        @Test
        public void testConfigurationPropertiesWithFactory(@Autowired AtConfigurationProperties atConfigurationProperties) {
            System.out.println(atConfigurationProperties);
            Assertions.assertThat(atConfigurationProperties)
                    .hasFieldOrPropertyWithValue("integer", 1)
                    .hasFieldOrPropertyWithValue("email", "99156629@qq.com")
                    .hasFieldOrPropertyWithValue("actualValue", "expectValue");
        }
    }
}
