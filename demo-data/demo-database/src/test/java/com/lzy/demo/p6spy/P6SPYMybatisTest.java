package com.lzy.demo.p6spy;

import com.lzy.demo.mybatis.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("p6spy")
@SpringBootTest
@SpringBootApplication
public class P6SPYMybatisTest extends MybatisPlusTest {

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        super.testInsert();
    }
}
