package com.lzy.demo.p6spy;

import com.lzy.demo.jpa.JpaQueryTest;
import com.lzy.demo.jpa.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("p6spy")
public class P6spyJPATest extends JpaQueryTest {

    /**
     * 测试sort
     */
    @Test
    public void testSort() {
        super.testSort();
    }
}
