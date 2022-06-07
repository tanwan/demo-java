package com.lzy.demo.spring.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;


/**
 * 测试spring-session
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("spring-session")
public class SessionTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RedisIndexedSessionRepository redisIndexedSessionRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试session
     *
     * @throws Exception the exception
     */
    @Test
    public void testSession() throws Exception {
        String sessionValue = "hello world";
        // 请求创建session
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session/create")
                .param("sessionValue", sessionValue)
        ).andDo(MockMvcResultHandlers.print()).andReturn();
        // redis的session不为null
        String cookieKey = new String(Base64.getDecoder().decode(mvcResult.getResponse().getCookie("SESSION").getValue()));
        Assertions.assertThat(redisIndexedSessionRepository.findById(cookieKey))
                .isNotNull();
        // 请求获取session
        mockMvc.perform(MockMvcRequestBuilders.get("/session/get")
                //使用同一个cookie
                .cookie(mvcResult.getResponse().getCookies()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionKey").value(sessionValue));
    }

    /**
     * 测试session过期
     *
     * @throws Exception the exception
     */
    @Test
    public void testSessionExpire() throws Exception {
        String sessionValue = "hello world";
        // 请求创建session
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session/create")
                .param("sessionValue", sessionValue)
        ).andReturn();
        // redis的session不为null
        String cookieKey = new String(Base64.getDecoder().decode(mvcResult.getResponse().getCookie("SESSION").getValue()));
        Assertions.assertThat(redisIndexedSessionRepository.findById(cookieKey))
                .isNotNull();
        // 请求获取session
        mockMvc.perform(MockMvcRequestBuilders.get("/session/get")
                //使用同一个cookie
                .cookie(mvcResult.getResponse().getCookies()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionKey").value(sessionValue));

        // 当session过期后(过期时间为3s)
        Thread.sleep(4000);
        // 使用RedisOperationsSessionRepository获取不到缓存
        Assertions.assertThat(redisIndexedSessionRepository.findById(cookieKey))
                .isNull();
        // 但是redis的session其实还没有删除
        // spring-session虽然默认每分钟执行一次清理redis,但是清理的只是spring:expirations:的内容
        // 对于spring:session的内容(过期时间是session的过期时间加5分钟),只是遍历去访问redis,让redis触发清理动作,这个实现感觉spring实现得不太好
        String redisKey = "spring:session:sessions:" + cookieKey;
        // 这边需要的key需要使用string序列化,所以这边直接用了StringRedisTemplate
        Assertions.assertThat(stringRedisTemplate.boundHashOps(redisKey).entries())
                .isNotEmpty();
    }

    /**
     * 测试监听
     *
     * @throws Exception the exception
     */
    @Test
    public void testListener() throws Exception {
        String sessionValue = "hello world";
        // 请求创建session
        mockMvc.perform(MockMvcRequestBuilders.get("/session/create")
                .param("sessionValue", sessionValue)
        );

        Thread.sleep(5000);
    }

    @SpringBootApplication
    @EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3)
    public static class SessionApplication {

    }
}
