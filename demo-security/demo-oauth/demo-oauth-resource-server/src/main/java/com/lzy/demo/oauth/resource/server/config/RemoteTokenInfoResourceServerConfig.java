/*
 * Created by LZY on 2017/4/27 22:50.
 */
package com.lzy.demo.oauth.resource.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 资源服务器,使用远程校验token
 *
 * @author LZY
 * @version v1.0
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration
 */
@Profile("token-info")
@Configuration
@EnableResourceServer
public class RemoteTokenInfoResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String CHECK_TOKEN = "http://127.0.0.1:18080/oauth/check_token";

    /**
     * 资源服务器的配置
     *
     * @param resources resources
     * @see org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 使用远程校验token
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(CHECK_TOKEN);
        // 校验token需要权限,因此需要有clientId和clientSecret,可以为资源服务器分配一个clientId和clientSecret
        remoteTokenServices.setClientId("resourceServer");
        remoteTokenServices.setClientSecret("secret");
        // 如果是无状态,则不能使用session,也就是被oauth保护的资源只能通过oauth2访问,不能直接使用spring-security访问
        resources
                //设置该资源服务器的id,跟ClientDetails#getResourceIds对应
                //.resourceId()
                .stateless(false)
                .tokenServices(remoteTokenServices);
    }

    /**
     * 设置oauth资源的访问规则
     *
     * @param http http
     * @throws Exception exception
     * @see org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods
     * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //只有/oauth2/**的才使用oauth2拦截
                .requestMatchers().antMatchers("/oauth2/**")
                .and()
                .authorizeRequests()
                //只要是oauth2的就可以访问
                .antMatchers("/oauth2/only-oauth2").access("#oauth2.isOAuth()")
                //oauth2或者spring security都能访问,如果要使用spring-security,则必须配置spring-security
                .antMatchers("/oauth2/oauth-or-security").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
                //因为ResourceServerConfiguration会把session策略设置为无状态的,如果要同时使用spring security和oauth,则需要修改session策略
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        ;
    }
}
