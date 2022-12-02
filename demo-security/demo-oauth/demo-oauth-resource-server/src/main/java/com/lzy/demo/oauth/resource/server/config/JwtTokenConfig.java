package com.lzy.demo.oauth.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

/**
 * 资源服务器配置,使用jwt
 *
 * @author LZY
 * @version v1.0
 */
@Profile("jwt")
@EnableWebSecurity
@Configuration
public class JwtTokenConfig {

    /**
     * oauth的配置
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     * @see DefaultBearerTokenResolver#resolve(HttpServletRequest) 解析token
     * @see AuthorityAuthorizationManager#check(Supplier, Object) 校验oauth的权限
     * @see BearerTokenAuthenticationEntryPoint 处理认证异常
     * @see OAuth2ResourceServerJwtConfiguration jwt的配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauth2/**").hasAuthority("SCOPE_message:read")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }
}
