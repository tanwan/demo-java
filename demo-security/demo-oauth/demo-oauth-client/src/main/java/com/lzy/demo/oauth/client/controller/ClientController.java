/*
 * Created by LZY on 2017/7/9 21:37.
 */
package com.lzy.demo.oauth.client.controller;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The type Client controller.
 *
 * @author LZY
 * @version v1.0
 */
@RestController
public class ClientController {

    @Resource(name = "authorizationCodeTemplate")
    private OAuth2RestTemplate authorizationCodeTemplate;

    @Resource(name = "clientCredentialsTemplate")
    private OAuth2RestTemplate clientCredentialsTemplate;

    /**
     * 授权码模式,授权码模式必须是登陆用户
     *
     * @return the string
     * @see AccessTokenProviderChain#obtainAccessToken
     */
    @GetMapping("/authorization-code")
    public String authorizationCode() {
        return authorizationCodeTemplate.getForObject("http://localhost:8080/oauth2/only-oauth2", String.class);
    }

    /**
     * 客户端模式
     *
     * @return the string
     */
    @GetMapping("/client-credentials")
    public String clientCredentials() {
        return clientCredentialsTemplate.getForObject("http://localhost:8080/oauth2/only-oauth2", String.class);
    }
}
