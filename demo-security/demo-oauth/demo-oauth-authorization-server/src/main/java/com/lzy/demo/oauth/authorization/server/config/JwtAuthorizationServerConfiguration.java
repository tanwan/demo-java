/*
 * Created by LZY on 2017/4/27 22:52.
 */
package com.lzy.demo.oauth.authorization.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * 授权服务器
 *
 * @author LZY
 * @version v1.0
 */
@Configuration
@EnableAuthorizationServer
@Profile("jwt")
public class JwtAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource(name = "customClientDetailsService")
    private ClientDetailsService clientDetailsService;

    /**
     * 配置clientDetailsService
     *
     * @param clients the client details configurer
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }


    /**
     * 配置授权服务器的非安全项
     *
     * @param endpoints the endpoints configurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //配置tokenStore
                .tokenStore(new JwtTokenStore(jwtAccessTokenConverter()))
                //jwt需要使用TokenEnhancer来对access_token进行增强,如果要配置多个Enhancer,则使用TokenEnhancerChain
                .tokenEnhancer(jwtAccessTokenConverter())
                //配置认证管理器,不配置的话,无法使用密码模式
                .authenticationManager(authenticationManager)
                //设置approvalHandler
                .userApprovalHandler(userApprovalHandler())
                //获取access允许使用get和post
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 配置授权服务器的安全项
     *
     * @param security a fluent configurer for security features
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //默认使用DelegatingPasswordEncoder
                //.passwordEncoder()
                //client_id和client_secret可以放在表单中
                .allowFormAuthenticationForClients()
                //检验token(oauth/check_token)的权限
                .checkTokenAccess("isFullyAuthenticated()")
                //获取公钥(/oauth/token_key)的权限
                .tokenKeyAccess("permitAll()");
    }

    /**
     * 用来确定当前用户是否批准此客户端的请求
     *
     * @return the user approval handler
     */
    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserApprovalHandler userApprovalHandler() {
        //使用用户的授权记录来判断用户是否批准
        ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
        //存储用户的授权记录
        userApprovalHandler.setApprovalStore(new InMemoryApprovalStore());
        userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        userApprovalHandler.setClientDetailsService(clientDetailsService);
        return userApprovalHandler;
    }

    /**
     * JwtAccessTokenConverter jwt和oauth授权的转换
     * 密钥使用以下命令生成
     * keytool -genkey -alias demo -keyalg RSA -keysize 1024 -keystore demo.jks -validity 365
     *
     * @return the jwt access token converter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称密钥,如果使用对称密码,资源服务器进行jwt解析的时候,直接使用对称密码
        //converter.setSigningKey("123456");
        //非对称密钥,如果使用非对称密码,资源服务器进行jwt解析的时候,需要来获取公钥
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 使用nimbusds-jose-jwt需要将KeyPair生成spring bean
     *
     * @return the key pair
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("demo.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("demo");
    }
}
