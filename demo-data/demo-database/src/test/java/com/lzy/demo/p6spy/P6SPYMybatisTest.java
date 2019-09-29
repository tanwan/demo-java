/*
 * Created by lzy on 2019/9/28 10:36 AM.
 */
package com.lzy.demo.p6spy;

import com.lzy.demo.mybatis.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author lzy
 * @version v1.0
 */
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
