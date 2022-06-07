package com.lzy.demo.flyway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@SpringBootApplication
@ActiveProfiles("flyway")
public class FlywayTest {


    /**
     * 测试flyway
     */
    @Test
    public void testFlyway() {

    }
}
