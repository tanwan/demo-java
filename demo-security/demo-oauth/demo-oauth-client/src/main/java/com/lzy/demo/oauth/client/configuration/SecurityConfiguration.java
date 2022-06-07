package com.lzy.demo.oauth.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * spring-security配置
 *
 * @author LZY
 * @version v1.0
 */
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * 配置SecurityFilterChain
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .mvcMatchers("/use-authorization-code").permitAll()
                        .mvcMatchers("/client-credentials-token", "/use-client-credentials").permitAll()
                        .anyRequest().authenticated()).formLogin(Customizer.withDefaults())
                // oauth2的配置都使用默认的
                .oauth2Login(Customizer.withDefaults()).oauth2Client(Customizer.withDefaults());
        return http.build();
    }
}
