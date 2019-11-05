/*
 * Created by LZY on 2017/4/26 19:38.
 */
package com.lzy.demo.oauth.resource.server.config;

import com.lzy.demo.oauth.resource.server.custom.AjaxAuthenticationSuccessHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring security的配置,jose-jwt不需要使用这个
 *
 * @author LZY
 * @version v1.0
 */
@EnableWebSecurity
@Profile("!jose-jwt")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .requestMatchers().antMatchers("/login").and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(new AjaxAuthenticationSuccessHandler())
                .permitAll();
    }
}
