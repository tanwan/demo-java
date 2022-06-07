package com.lzy.demo.oauth.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * 配置WebClient
     *
     * @param authorizedClientManager the authorized client manager
     * @return the web client
     * @see OAuth2LoginAuthenticationFilter#attemptAuthentication 授权码模式,授权服务器重定向回来的过滤器
     * @see OAuth2AuthorizationRequestRedirectFilter 授权码模式,当请求/oauth2/authorization/{registrationId}时,就会重定向到authorization-uri
     * @see SavedRequestAwareAuthenticationSuccessHandler 负责保存需要认证前的请求,等认证后再重定向回去,比如授权码模式,再重定向回/use-authorization-code
     */
    @Bean
    public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        // 可以为这个WebClient设置默认的client,这样使用WebClient的时候,就不需要再继续指定了,不过这个要注意的是,使用WebClient的请求都会带上token
        // oauth2.setDefaultOAuth2AuthorizedClient(true);
        // oauth2.setDefaultClientRegistrationId("demo-oauth-client");
        return WebClient.builder()
                .baseUrl("http://127.0.0.1:8080/")
                .apply(oauth2.oauth2Configuration())
                .build();
    }


    /**
     * 配置OAuth2AuthorizedClientManager
     * WebClient和@RegisteredOAuth2AuthorizedClient用来获取OAuth2AuthorizedClient的
     *
     * @param clientRegistrationRepository 还未进行获取token的client Repository,也就是在application.yml配置的client
     * @param authorizedClientRepository   已经获取过token的client Repository, AuthenticatedPrincipalOAuth2AuthorizedClientRepository包含了HttpSessionOAuth2AuthorizedClientRepository
     *                                     如果当前Principal是非认证的,则会使用HttpSessionOAuth2AuthorizedClientRepository
     *                                     已经认证的也可以存储在DB中,See JdbcOAuth2AuthorizedClientService,sql脚本在该类的同级包下
     * @return OAuth2AuthorizedClientManager
     * @see DefaultOAuth2AuthorizedClientManager#authorize(OAuth2AuthorizeRequest)
     */
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                                 OAuth2AuthorizedClientRepository authorizedClientRepository) {
        // 认证方式
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .clientCredentials()
                .build();
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }
}
