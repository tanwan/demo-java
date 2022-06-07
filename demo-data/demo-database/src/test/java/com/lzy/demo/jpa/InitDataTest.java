package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = JpaApplication.class)
@ActiveProfiles({"jpa", "init"})
public class InitDataTest {

    /**
     * 测试数据初始化
     */
    @Test
    public void testInitData() {
    }

}
