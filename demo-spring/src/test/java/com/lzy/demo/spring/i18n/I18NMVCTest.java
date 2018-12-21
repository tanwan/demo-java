/*
 * Created by lzy on 2018-12-20 13:49.
 */
package com.lzy.demo.spring.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * mvc国际化测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest
@TestPropertySource(properties = {"spring.messages.basename=i18n/messages"})
@SpringJUnitConfig({MessageSourceAutoConfiguration.class, I18NMVCTest.I18NController.class})
@AutoConfigureMockMvc
public class I18NMVCTest {
    /**
     * 测试mvc的i18n
     *
     * @param mockMvc the mock mvc
     * @throws Exception the exception
     */
    @Test
    public void testMVC(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test-i18n").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * The type 18 n controller.
     */
    @RestController
    public static class I18NController {
        @Resource
        private MessageSource messageSource;

        /**
         * 通过LocaleResolver来解析国际化的
         *
         * @param locale the locale
         * @return the string
         * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#localeResolver()
         */
        @GetMapping("/test-i18n")
        public String testI18N(Locale locale) {
            return messageSource.getMessage("code", null, locale);
        }
    }
}
