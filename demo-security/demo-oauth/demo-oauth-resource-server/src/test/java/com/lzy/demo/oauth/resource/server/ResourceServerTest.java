package com.lzy.demo.oauth.resource.server;


import com.lzy.demo.oauth.resource.server.config.JwtTokenConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ResourceServerTest {

    /**
     * 使用jwt的测试
     */
    @WebMvcTest
    @Import(JwtTokenConfig.class)
    public static class JwtTokenTest {
        /**
         * 测试使用Jwt
         *
         * @param mockMvc the mock mvc
         * @throws Exception the exception
         */
        @Test
        public void testJwt(@Autowired MockMvc mockMvc) throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/jwt")
                            .with(SecurityMockMvcRequestPostProcessors.jwt()
                                    // 自定义一些jwt的信息,可以使用claim添加scope
                                    .jwt(jwt -> jwt.subject("test").claim("scope", "message:read"))))
                    .andExpect(MockMvcResultMatchers.content().string("{sub=test, scope=message:read}:test"))
                    .andDo(MockMvcResultHandlers.print());

            mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/jwt")
                            .with(SecurityMockMvcRequestPostProcessors.jwt()
                                    // 也可以通过authorities添加scope,需要添加SCOPE_前缀
                                    .authorities(new SimpleGrantedAuthority(("SCOPE_message:read")))))
                    .andDo(MockMvcResultHandlers.print());
        }
    }
}
