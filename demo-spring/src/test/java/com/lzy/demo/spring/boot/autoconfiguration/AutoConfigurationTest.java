/*
 * Created by lzy on 2018/11/6 6:46 PM.
 */
package com.lzy.demo.spring.boot.autoconfiguration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试springboot自动配置
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AutoConfigurationTest {

    /**
     * 测试spring boot自动配置
     */
    @Test
    public void testAutoConfiguration() {
    }
}
