package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.CrosFilterConfig;
import com.lzy.demo.spring.mvc.controller.SimpleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * cros测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = {MVCApplication.class, SimpleRestController.class, CrosFilterConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CrosTest {

    /**
     * 测试cros
     *
     * @throws Exception the exception
     */
    @Test
    public void startApplication() throws Exception {
        Thread.sleep(1000000);
    }
}
