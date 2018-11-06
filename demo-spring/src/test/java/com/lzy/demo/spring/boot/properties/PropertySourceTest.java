/*
 * Created by lzy on 2018/11/5 9:46 PM.
 */
package com.lzy.demo.spring.boot.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code @PropertySource}测试
 * @author lzy
 * @version v1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PropertySourceConfig.class)
public class PropertySourceTest {

    /**
     * 测试@PropertySource
     *
     * @param key the key
     */
    @Test
    public void testPropertySource(@Value("${key}") String key) {
        Assertions.assertEquals("value", key);
    }
}
