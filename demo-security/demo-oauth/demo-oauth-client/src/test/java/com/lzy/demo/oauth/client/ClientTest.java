package com.lzy.demo.oauth.client;

import com.lzy.demo.oauth.client.configuration.SecurityConfiguration;
import com.lzy.demo.oauth.client.controller.ClientController;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;

@WebMvcTest
@Import({SecurityConfiguration.class, ClientController.class})
public class ClientTest {
    private static final MockWebServer mockWebServer = new MockWebServer();

    @Autowired
    private MockMvc mockMvc;


    /**
     * 测试客户端模式
     *
     * @throws Exception the exception
     */
    @Test
    void testClientCredentials() throws Exception {
        // mock server
        mockWebServer.enqueue(new MockResponse().setBody("body").setResponseCode(200));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/use-client-credentials")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Client("any-client-id")))
                .andExpect(MockMvcResultMatchers.content().string("body"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * 测试授权码模式
     *
     * @throws Exception the exception
     */
    @Test
    void testAuthorizationCode() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("body").setResponseCode(200));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/use-authorization-code")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login())
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Client("any-client-id")))
                .andExpect(MockMvcResultMatchers.content().string("body"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Configuration
    static class WebClientConfig {

        @Bean
        WebClient webClient() {
            return WebClient.create(mockWebServer.url("/").toString());
        }
    }

    @AfterAll
    static void shutdown() throws Exception {
        mockWebServer.shutdown();
    }
}
