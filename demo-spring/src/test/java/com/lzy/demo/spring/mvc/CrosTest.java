/*
 * Created by lzy on 2019/9/24 5:31 PM.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.CrosFilterConfig;
import com.lzy.demo.spring.mvc.controller.SampleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * cros测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringJUnitConfig(classes = {SampleRestController.class, CrosFilterConfig.class})
public class CrosTest {

    /**
     * 测试cros
     *
     * @throws Exception the exception
     */
    @Test
    public void testCros() throws Exception {
        Thread.sleep(1000000);
    }


}
