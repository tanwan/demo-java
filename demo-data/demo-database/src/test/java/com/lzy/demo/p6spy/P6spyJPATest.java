/*
 * Created by lzy on 2019/9/28 10:36 AM.
 */
package com.lzy.demo.p6spy;

import com.lzy.demo.jpa.JpaQueryTest;
import com.lzy.demo.jpa.application.JpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-p6spy.yml")
public class P6spyJPATest extends JpaQueryTest {

    /**
     * 测试sort
     */
    @Test
    public void testSort() {
        super.testSort();
    }
}
