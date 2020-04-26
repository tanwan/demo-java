/*
 * Created by lzy on 2019-06-11 16:45.
 */
package com.lzy.demo.spring.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


/**
 * 测试spring-session的cookie
 *
 * @author lzy
 * @version v1.0
 */
public class SessionIdTest {

    /**
     * 使用ServerProperties指定SessionId
     */
    @ActiveProfiles("server-properties")
    public static class UseServerProperties extends SessionTest {

        /**
         * 测试使用ServerProperties指定SessionId
         *
         * @param mockMvc the mock mvc
         * @throws Exception the exception
         */
        @Test
        public void testUseServerProperties(@Autowired MockMvc mockMvc) throws Exception {
            String sessionValue = "hello world";
            // 请求创建session
            mockMvc.perform(MockMvcRequestBuilders.get("/session/create")
                    .param("sessionValue", sessionValue)
            ).andExpect(result -> Assertions.assertThat(result.getResponse().getCookie("PROPERTIES_COOKIE_NAME")).isNotNull());
        }
    }

    /**
     * 使用java config指定SessionId
     */
    @SpringJUnitConfig(classes = UseJavaConfig.CookieConfig.class)
    public static class UseJavaConfig extends SessionTest {
        private static final String COOKIE_NAME = "COOKIE_NAME";

        @Configuration
        public static class CookieConfig {
            /**
             * 配置session的cookie
             *
             * @return the cookie serializer
             * @see SpringHttpSessionConfiguration#init()
             * @see SpringHttpSessionConfiguration#setCookieSerializer(org.springframework.session.web.http.CookieSerializer)
             */
            @Bean
            public CookieSerializer cookieSerializer() {
                DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
                defaultCookieSerializer.setCookieName(COOKIE_NAME);
                return defaultCookieSerializer;
            }
        }

        /**
         * 测试使用java config指定SessionId
         *
         * @param mockMvc the mock mvc
         * @throws Exception the exception
         */
        @Test
        public void testUseJavaConfig(@Autowired MockMvc mockMvc) throws Exception {
            String sessionValue = "hello world";
            // 请求创建session
            mockMvc.perform(MockMvcRequestBuilders.get("/session/create")
                    .param("sessionValue", sessionValue)
            ).andExpect(result -> Assertions.assertThat(result.getResponse().getCookie(COOKIE_NAME)).isNotNull());
        }
    }

    /**
     * sessionId保存的header
     */
    @SpringJUnitConfig(classes = UseHeadSessionId.HeaderHttpSessionIdResolverConfig.class)
    public static class UseHeadSessionId extends SessionTest {
        private static final String HEADER_NAME = "HEADER_NAME";

        @Configuration
        public static class HeaderHttpSessionIdResolverConfig {
            /**
             * 配置session的cookie
             *
             * @return the cookie serializer
             * @see SpringHttpSessionConfiguration#setHttpSessionIdResolver(org.springframework.session.web.http.HttpSessionIdResolver)
             * @see SpringHttpSessionConfiguration#springSessionRepositoryFilter(org.springframework.session.SessionRepository)
             */
            @Bean
            public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
                return new HeaderHttpSessionIdResolver(HEADER_NAME);
            }
        }

        /**
         * 测试使用java config指定Session的cookie
         *
         * @param mockMvc the mock mvc
         * @throws Exception the exception
         */
        @Test
        public void testUseJavaConfig(@Autowired MockMvc mockMvc) throws Exception {
            String sessionValue = "hello world";
            // 请求创建session
            mockMvc.perform(MockMvcRequestBuilders.get("/session/create")
                    .param("sessionValue", sessionValue)
            ).andExpect(result -> Assertions.assertThat(result.getResponse().getHeader(HEADER_NAME)).isNotNull());
        }
    }
}
