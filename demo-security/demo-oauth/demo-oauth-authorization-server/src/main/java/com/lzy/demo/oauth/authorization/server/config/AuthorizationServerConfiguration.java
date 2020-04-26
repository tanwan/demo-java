/*
 * Created by LZY on 2017/4/27 22:52.
 */
package com.lzy.demo.oauth.authorization.server.config;

import com.lzy.demo.oauth.authorization.server.custom.CustomTokenEnhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * 授权服务器
 *
 * @author LZY
 * @version v1.0
 */
@Configuration
@EnableAuthorizationServer
@Profile("default")
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource(name = "customClientDetailsService")
    private ClientDetailsService clientDetailsService;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置ClientDetailsService
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
                //调用UserDetailsService,不设置的话,刷新access_token会报错
                .userDetailsService(userDetailsService)
                //配置tokenStore,token使用此类来进行存取
                .tokenStore(tokenStore())
                //配置TokenEnhancer,如果要配置多个Enhancer,则使用TokenEnhancerChain
                .tokenEnhancer(new CustomTokenEnhancer())
                //配置认证管理器,不配置的话,无法使用密码模式
                .authenticationManager(authenticationManager)
                //设置approvalHandler
                .userApprovalHandler(userApprovalHandler())
                //获取access允许使用get和post
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        //配置授权模式生成code的service,默认使用InMemoryAuthorizationCodeServices
        //.authorizationCodeServices()
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

        //使用已存在的token判断用户是否批准
        //使用此UserApprovalHandler在用户授权页默认不显示scope
        //TokenStoreUserApprovalHandler tokenStoreUserApprovalHandler = new TokenStoreUserApprovalHandler();
        //tokenStoreUserApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        //tokenStoreUserApprovalHandler.setTokenStore(tokenStore());
        //tokenStoreUserApprovalHandler.setClientDetailsService(clientDetailsService);
        //return tokenStoreUserApprovalHandler;
    }

    @Bean
    public TokenStore tokenStore() {
        // 这边默认使用的是jdk序列化,注意版本不同的话,反序列化可能会失败
        return new RedisTokenStore(redisConnectionFactory);
    }
}
