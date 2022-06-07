package com.lzy.demo.oauth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RestController
public class ClientController {

    @Autowired
    private WebClient webClient;

    /**
     * 授权码模式
     *
     * @return the string
     */
    @GetMapping("/use-authorization-code")
    public String authorizationCode() {
        return webClient.get().uri("oauth2/jwt")
                .attributes(clientRegistrationId("demo-oauth-code"))
                .retrieve().bodyToMono(String.class)
                .block();
    }

    /**
     * 客户端模式
     *
     * @return the string
     */
    @GetMapping("/use-client-credentials")
    public String clientCredentials() {
        return webClient
                .get().uri("oauth2/jwt")
                // 指定client-id
                .attributes(clientRegistrationId("demo-oauth-client"))
                .retrieve().bodyToMono(String.class)
                .block();
    }


    /**
     * 使用OAuth2AuthorizedClient获取token
     *
     * @param authorizedClient the authorized client
     * @return the string
     */
    @GetMapping("/client-credentials-token")
    public String clientCredentialsToken(@RegisteredOAuth2AuthorizedClient("demo-oauth-client") OAuth2AuthorizedClient authorizedClient) {
        // 可以使用@RegisteredOAuth2AuthorizedClient
        return authorizedClient.getAccessToken().getTokenValue();
    }
}
