package com.lzy.demo.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring-security配置
 * 如果需要有多个HttpSecurity对象,则可以配置多个WebSecurityConfigurerAdapter实例(多个使用@Order控制顺序)
 *
 * @author LZY
 * @version v1.0
 */
@Profile("jwt")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class JwtSecurityConfig {
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        //可以配置一些需要忽略的资源,每个路径对应一个SecurityFilterChain
        return web -> web.ignoring().requestMatchers("/static/**");
    }


    @Bean
    public DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter("/login");
        //对应着一个SecurityFilterChain
        http
                .securityMatchers()
                //此HttpSecurity的配置只对/login,/logout,/security/**,/secured/**,/jsr250/**,/pre-post/**有效,默认为/**
                .requestMatchers("/login", "/logout", "/security/**", "/secured/**", "/jsr250/**", "/pre-post/**")
                .and()
                // jwt认证的过滤器在原来认证过滤器之前
                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                // jwt过滤器在jwt认证过滤器之后
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                // 关闭csrf配置
                .csrf().disable()
                // cors配置,这里如果不配置CorsConfigurationSource的话,则会从spring容器中获取CorsConfigurationSource的实例
                .cors()
                .and()
                //使用ExpressionUrlAuthorizationConfigurer
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                //任何人都可以访问
                                .requestMatchers("/security/permit-all").permitAll()
                                //角色为ROLE_USER才可以访问
                                .requestMatchers("/security/user").hasRole("USER")
                                .requestMatchers("/security/authority").hasAuthority("ROLE_USER")
                                //角色为ROLE_USER或者ROLE_ADMIN可以访问
                                .requestMatchers("/security/any-role").hasAnyRole("USER", "ADMIN")
                                //角色为ROLE_USER并且为ROLE_ADMIN才可以访问
                                .requestMatchers("/security/admin").access(new WebExpressionAuthorizationManager("hasRole('USER') and hasRole('ADMIN')"))
                                //特定ip才可以访问
                                 .requestMatchers("/security/ip").access(new WebExpressionAuthorizationManager("hasIpAddress('127.0.0.1')"))
                                //只有匿名用户可以访问
                                .requestMatchers("/security/anonymous").anonymous()
                                //任何人都不能访问
                                .requestMatchers("/security/deny-all").denyAll()
                                //只有通过rememberMe cookie登录的用户可以访问,其它都不能访问
                                .requestMatchers("/security/remember-me").rememberMe()
                                //只要有认证过或者使用rememberMe cookie登录的用户就可以访问
                                .requestMatchers("/security/authenticated").authenticated()
                                //必需使用账号密码认证,使用rememberMe cookie登录的用户无法访问
                                .requestMatchers("/security/fullyAuthenticated").fullyAuthenticated()
                                //其它请求(只针对requestMatchers().antMatchers配置的/login, /logout, /security/**)需要认证
                                .anyRequest().fullyAuthenticated()
                )
                //配置rememberMe
                .rememberMe()
                .and()
                // 配置登陆
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login.html")
                                // 登录处理url,默认为/login
                                .loginProcessingUrl("/login")
                                //默认为/(首页)
                                .defaultSuccessUrl("/index.html")
                                // ajax登录请求处理
                                //.successHandler(new AjaxAuthenticationSuccessHandler())
                                //.failureHandler(new AjaxAuthenticationFailureHandler())
                                .permitAll()
                )
                // 配置异常处理
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        DefaultSecurityFilterChain chain = http.build();
        jwtLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        return chain;
    }
}
