/*
 * Created by lzy on 2019-08-07 13:31.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.LocaleConfig;
import com.lzy.demo.spring.mvc.controller.SimpleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.util.Locale;

/**
 * 自定义国际化测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SpringJUnitConfig(classes = {SimpleRestController.class, LocaleConfig.class})
public class LocaleTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试国际化
     *
     * @throws Exception the exception
     * @see LocaleConfig
     * @see ServletRequestMethodArgumentResolver#resolveArgument(java.lang.Class, javax.servlet.http.HttpServletRequest)
     */
    @Test
    public void testLocale() throws Exception {
        // cookie指定国际化
        Cookie cookie = new Cookie("zone", "zh-CN");
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/locale")
                // 注意这边要使用-,不能使用下划线
                .cookie(cookie))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(Locale.SIMPLIFIED_CHINESE.toString()))
                .andReturn();
    }
}
