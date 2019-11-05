/*
 * Created by LZY on 2017/7/9 21:38.
 */
package com.lzy.demo.oauth.client.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.redirect.AbstractRedirectResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;

import javax.annotation.Resource;

/**
 * The type Template configuration.
 *
 * @author LZY
 * @version v1.0
 */
@EnableOAuth2Client
@Configuration
public class TemplateConfiguration {


    /**
     * @see OAuth2ClientConfiguration#accessTokenRequest
     */
    @Resource
    @Qualifier("accessTokenRequest")
    private AccessTokenRequest accessTokenRequest;

    /**
     * 授权码模式OAuth2RestTemplate,这边其实可以使用spring-security-oauth2-autoconfigure的
     *
     * @param oauth2ClientContext    Session级别的
     * @param oAuth2ClientProperties the o auth 2 client properties
     * @return the o auth 2 rest template
     * @see org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ProtectedResourceDetailsConfiguration
     * @see OAuth2ClientConfiguration.OAuth2ClientContextConfiguration#oauth2ClientContext
     */
    @Bean
    public OAuth2RestTemplate authorizationCodeTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ClientProperties oAuth2ClientProperties) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(new AuthorizationCodeResourceDetails(), oAuth2ClientProperties, "code"), oauth2ClientContext);
    }


    /**
     * 客户端类型的oauth2RestTemplate,这边其实可以使用spring-security-oauth2-autoconfigure的
     *
     * @param oAuth2ClientProperties the o auth 2 client properties
     * @return the o auth 2 rest template
     */
    @Bean
    public OAuth2RestTemplate clientCredentialsTemplate(OAuth2ClientProperties oAuth2ClientProperties) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(new ClientCredentialsResourceDetails(), oAuth2ClientProperties, "client"), clientOauth2ClientContext());
    }

    /**
     * 上下文, 这边声明的是客户端的上下文,授权码的上下文使用spring-security-oauth2-autoconfigure
     *
     * @return the o auth 2 client context
     * @see OAuth2ClientConfiguration.OAuth2ClientContextConfiguration#oauth2ClientContext
     */
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2ClientContext clientOauth2ClientContext() {
        return new DefaultOAuth2ClientContext(accessTokenRequest);
    }

    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails(BaseOAuth2ProtectedResourceDetails details, OAuth2ClientProperties oAuth2ClientProperties, String registrationName) {
        OAuth2ClientProperties.Provider provider = oAuth2ClientProperties.getProvider().get("demo-oauth");
        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get(registrationName);
        details.setAccessTokenUri(provider.getTokenUri());
        details.setClientId(registration.getClientId());
        details.setClientSecret(registration.getClientSecret());
        // 授权码模式需要跳转到授权页面
        if (details instanceof AbstractRedirectResourceDetails) {
            ((AbstractRedirectResourceDetails) details).setUserAuthorizationUri(provider.getAuthorizationUri());
        }
        return details;
    }
}
