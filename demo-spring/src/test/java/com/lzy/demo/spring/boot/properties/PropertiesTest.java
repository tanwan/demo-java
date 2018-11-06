/*
 * Created by lzy on 2018/11/1 9:45 PM.
 */
package com.lzy.demo.spring.boot.properties;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code @ConfigurationProperties}和{@code @Value}测试
 *
 * @author lzy
 * @version v1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(AtConfigurationProperties.class)
//spring.config.location可以指定springboot配置文件的路径
@TestPropertySource(properties = "spring.config.location=classpath:properties.yml")
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = {CustomConverter.class, AtValue.class})
public class PropertiesTest {

    /**
     * 测试@ConfigurationProperties
     *
     * @param atConfigurationProperties the at configuration properties
     */
    @Test
    public void testConfigurationProperties(@Autowired AtConfigurationProperties atConfigurationProperties) {
        System.out.println(atConfigurationProperties);
    }

    /**
     * 测试@Value.
     *
     * @param atValue the at value
     */
    @Test
    public void testAtValue(@Autowired AtValue atValue) {
        System.out.println(atValue);
    }


}
