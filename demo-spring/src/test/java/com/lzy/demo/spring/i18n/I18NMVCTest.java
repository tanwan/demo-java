package com.lzy.demo.spring.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Locale;

/**
 * mvc国际化测试
 *
 * @author lzy
 * @version v1.0
 */
@WebMvcTest
@TestPropertySource(properties = {"spring.messages.basename=i18n/messages"})
@SpringJUnitConfig({I18NMVCTest.I18NController.class})
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
                .andExpect(MockMvcResultMatchers.content().string("英文"))
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
