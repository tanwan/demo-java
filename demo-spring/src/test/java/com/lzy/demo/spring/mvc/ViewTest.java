/*
 * Created by lzy on 2019-08-03 17:22.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.controller.ViewController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 视图测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@SpringJUnitConfig(classes = ViewController.class)
public abstract class ViewTest {


    /**
     * 开启thymeleaf
     */
    @TestPropertySource(properties = "spring.thymeleaf.enabled=true")
    public static class EnableThymeleafTest extends ViewTest {
        /**
         * 测试View
         * 使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void testView() throws Exception {
            Thread.sleep(1000000);
        }
    }


    /**
     * 禁用thymeleaf
     */
    @TestPropertySource(properties = "spring.thymeleaf.enabled=false")
    public static class DisableThymeleafTest extends ViewTest {
        /**
         * 测试View
         * 使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void testView() throws Exception {
            Thread.sleep(1000000);
        }
    }
}
