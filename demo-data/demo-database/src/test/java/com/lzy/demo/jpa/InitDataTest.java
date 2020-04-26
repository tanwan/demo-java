/*
 * Created by lzy on 2019-08-10 11:18.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 初始化数据测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-init-data.yml")
public class InitDataTest {

    /**
     * 测试数据初始化
     *
     * @throws ClassNotFoundException the class not found exception
     */
    @Test
    public void testInitData() throws ClassNotFoundException {
    }

}
