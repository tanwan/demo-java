package com.lzy.demo.oauth.authorization.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 参考demo-security的SecurityConfig
 *
 * @author LZY
 * @version v1.0
 */
@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {


    /**
     * 配置WebSecurity
     *
     * @return the web security customizer
     * @see WebSecurity
     */
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        // h2-console不需要访问权限
        return web -> web.ignoring().requestMatchers("/h2-console/**")
                .requestMatchers("/*.html", "/.demo-private-key-jwt/jwks.json");
    }

    /**
     * 配置HttpSecurity
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     * @see HttpSecurity
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    /**
     * 用户信息,这边使用InMemoryUserDetailsManager
     *
     * @return the user details service
     */
    @Bean
    public UserDetailsService users() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.builder()
                .username("lzy")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
