/*
 * Created by LZY on 2017/4/27 22:50.
 */
package com.lzy.demo.oauth.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

/**
 * 资源服务器,使用jwt
 *
 * @author LZY
 * @version v1.0
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration
 */
@Profile("jwt")
@Configuration
@EnableResourceServer
public class JwtResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 资源服务器的配置
     *
     * @param resources resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .stateless(true)
                .tokenStore(new JwtTokenStore(jwtAccessTokenConverter()));
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
                .antMatchers("/oauth2/only-oauth2").access("#oauth2.isOAuth()");
    }

    /**
     * JwtAccessTokenConverter实现了InitializingBean,因此需要声明成spring bean
     *
     * @return the jwt access token converter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 使用非对称密钥的公钥
        // 使用keytool -list -rfc -keystore demo.jks -storepass 123456 可以生成demo.jks的公钥签名,然后可以新建一个demo.cer文件用来存储公钥签名
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate c = cf.generateCertificate(getClass().getResourceAsStream("/demo.cer"));
            PublicKey publicKey = c.getPublicKey();
            String publicKeyString = "-----BEGIN PUBLIC KEY-----\n" + new String(Base64.getEncoder().encode(publicKey.getEncoded()))
                    + "\n-----END PUBLIC KEY-----";
            // 公钥也可以直接请求授权服务器的/oauth/token_key获取公钥
            converter.setVerifierKey(publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 可以使用对称密钥
        // converter.setSigningKey("123456");
        return converter;
    }
}
