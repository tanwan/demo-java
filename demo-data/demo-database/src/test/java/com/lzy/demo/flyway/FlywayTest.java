/*
 * Created by lzy on 2019-07-31 19:54.
 */
package com.lzy.demo.flyway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试flyway
 *
 * @author lzy
 * @version v1.0
 */

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.config.location=classpath:flyway.yml")
@SpringBootTest
@SpringBootApplication
public class FlywayTest {


    @Test
    public void testFlyway() {

    }
}
