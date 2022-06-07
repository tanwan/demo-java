package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.controller.FileController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 文件上传测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = {MVCApplication.class, FileController.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.servlet.multipart.max-file-size=100MB"})
public class FileUploadTest {


    /**
     * 测试文件上传
     * 使用postman测试
     *
     * @throws Exception the exception
     */
    @Test
    public void startApplication() throws Exception {
        Thread.sleep(1000000);
    }
}
