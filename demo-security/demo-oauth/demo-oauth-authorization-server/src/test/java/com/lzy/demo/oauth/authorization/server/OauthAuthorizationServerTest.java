package com.lzy.demo.oauth.authorization.server;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lzy.demo.oauth.authorization.server.controller.JwkSetController;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OauthAuthorizationServerTest {
    private static final String CLIENT_ID = "demo-client";
    private static final String CLIENT_SECRET = "123456";
    private static final String REDIRECT_URI = "http://127.0.0.1:18080/redirect.html";

    @Resource
    private WebClient webClient;


    @Resource
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().clearCookies();
    }

    /**
     * 测试客户端模式获取token
     *
     * @throws Exception the exception
     */
    @Test
    public void testClientCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/oauth2/token")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
                        .param("grant_type", "client_credentials"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试授权码模式
     *
     * @throws Exception the exception
     */
    @Test
    public void testAuthorizationCode() throws Exception {
        // 登陆
        HtmlPage page = webClient.getPage("/login");
        HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
        HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
        HtmlButton signInButton = page.querySelector("button");
        usernameInput.type("lzy");
        passwordInput.type("123456");

        // 不成功则直接抛出异常,设置为false的话,可以获取响应的状态结果
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        // 不设置为重定向的话,可以获取到响应的状态结果
        webClient.getOptions().setRedirectEnabled(true);
        signInButton.click();

        // 用户授权
        String authorizationRequest = UriComponentsBuilder.fromPath("/oauth2/authorize")
                .queryParam("response_type", "code").queryParam("client_id", CLIENT_ID)
                .queryParam("scope", "openid message:read").queryParam("state", "some-state")
                .queryParam("redirect_uri", REDIRECT_URI).toUriString();
        page = this.webClient.getPage(authorizationRequest);
        HtmlCheckBoxInput checkBox = page.getHtmlElementById("message:read");
        checkBox.setChecked(true);
        HtmlElement submitButton = page.getHtmlElementById("submit-consent");

        // 禁止重定向
        webClient.getOptions().setRedirectEnabled(false);
        // 获取code
        WebResponse response = submitButton.click().getWebResponse();
        MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString(response.getResponseHeaderValue("location")).build().getQueryParams();
        String code = parameters.getFirst("code");

        System.out.println("code:" + code);

        // 获取token
        mockMvc.perform(MockMvcRequestBuilders.post("/oauth2/token")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
                        .param("grant_type", "authorization_code").param("code", code).param("redirect_uri", REDIRECT_URI))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试CLIENT_SECRET_JWT
     *
     * @throws Exception the exception
     * @see JwtClientAssertionAuthenticationProvider.JwtClientAssertionDecoderFactory#createDecoder(RegisteredClient)
     */
    @Test
    public void testClientSecretJwt() throws Exception {
        String clientId = "demo-client-secret-jwt";
        // 使用client的密钥作为jws的SecretKey生成JWT
        byte[] password = "12345678901234567890123456789012".getBytes(StandardCharsets.UTF_8);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), new JWTClaimsSet.Builder()
                // JWT需要iss(clientId),sub(clientId),aud(授权服务器的iss(ProviderSettings配置的)),exp
                .issuer(clientId).subject(clientId).audience("http://localhost")
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .build());
        signedJWT.sign(new MACSigner(password));
        mockMvc.perform(MockMvcRequestBuilders.post("/oauth2/token")
                        .param("grant_type", "client_credentials")
                        .param("client_id", clientId)
                        .param("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                        .param("client_assertion", signedJWT.serialize()))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * 测试元数据
     *
     * @throws Exception the exception
     */
    @Test
    public void testMetadata() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/.well-known/oauth-authorization-server"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * PRIVATE_KEY_JWT需要请求jwkseturl,所以这边需要确定的端口
     */
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    @AutoConfigureMockMvc
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public static class PrivateKeyJwtTest {
        @Resource
        private MockMvc mockMvc;

        /**
         * 测试PRIVATE_KEY_JWT
         *
         * @throws Exception the exception
         */
        @Test
        public void testPrivateKeyJwt() throws Exception {
            String clientId = "demo-private-key-jwt";
            // 使用client的密钥作为jws的SecretKey生成JWT
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), new JWTClaimsSet.Builder()
                    // JWT需要iss(clientId),sub(clientId),aud(授权服务器的iss(ProviderSettings配置的)),exp
                    .issuer(clientId).subject(clientId).audience("http://localhost")
                    .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                    .build());
            signedJWT.sign(new RSASSASigner(JwkSetController.PRIVATE_KEY_JWT_KEY_PAIR.getPrivate()));
            mockMvc.perform(MockMvcRequestBuilders.post("/oauth2/token")
                            .param("grant_type", "client_credentials")
                            .param("client_id", clientId)
                            .param("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                            .param("client_assertion", signedJWT.serialize()))
                    .andDo(MockMvcResultHandlers.print());
        }
    }

}
