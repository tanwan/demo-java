package com.lzy.demo.security.config;

import com.lzy.demo.security.filter.AddAtFillter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Profile("default")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        //可以配置一些需要忽略的资源,每个路径对应一个SecurityFilterChain
        return web -> web.ignoring().antMatchers("/static/**");
    }


    /**
     * HttpSecurity 配置
     *
     * @param http http
     * @return DefaultSecurityFilterChain
     * @throws Exception exception
     * @see WebExpressionVoter
     */
    @Bean
    public DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
        //对应着一个SecurityFilterChain
        http
                //此HttpSecurity的配置只对/login,/logout,/security/**,/secured/**,/jsr250/**,/pre-post/**有效,默认为/**
                .requestMatchers().antMatchers("/login", "/logout", "/security/**", "/secured/**", "/jsr250/**", "/pre-post/**")
                .and()
                //order跟指定过滤器一样,但会在指定过滤器之前执行
                .addFilterAt(new AddAtFillter(), UsernamePasswordAuthenticationFilter.class)
                // 关闭csrf配置
                .csrf().disable()
                // cors配置,这里如果不配置CorsConfigurationSource的话,则会从spring容器中获取CorsConfigurationSource的实例,所以也可以配置为Spring Bean
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                //任何人都可以访问
                .antMatchers("/security/permit-all").permitAll()
                // 这边的判断在WebExpressionVoter
                //角色为ROLE_USER才可以访问
                .antMatchers("/security/user").hasRole("USER")
                .antMatchers("/security/authority").hasAuthority("ROLE_USER")
                //角色为ROLE_USER或者ROLE_ADMIN可以访问
                .antMatchers("/security/any-role").hasAnyRole("USER", "ADMIN")
                //角色为ROLE_USER并且为ROLE_ADMIN才可以访问
                .antMatchers("/security/admin").access("hasRole('USER') and hasRole('ADMIN')")
                //特定ip才可以访问
                .antMatchers("/security/ip").hasIpAddress("127.0.0.1")
                //只有匿名用户可以访问
                .antMatchers("/security/anonymous").anonymous()
                //任何人都不能访问
                .antMatchers("/security/deny-all").denyAll()
                //只有通过rememberMe cookie登录的用户可以访问,其它都不能访问
                .antMatchers("/security/remember-me").rememberMe()
                //只要有认证过或者使用rememberMe cookie登录的用户就可以访问
                .antMatchers("/security/authenticated").authenticated()
                //必需使用账号密码认证,使用rememberMe cookie登录的用户无法访问
                .antMatchers("/security/fullyAuthenticated").fullyAuthenticated()
                //其它请求(只针对requestMatchers().antMatchers配置的/login, /logout, /security/**)需要认证
                .anyRequest().authenticated()
                .and()
                //配置rememberMe
                .rememberMe()
                .and()
                // 配置登录
                .formLogin()
                // 登录页面
                .loginPage("/login.html")
                // 登录处理url,默认为/login
                .loginProcessingUrl("/login")
                //默认为/(首页)
                .defaultSuccessUrl("/index.html")
                // ajax登录请求处理
                //.successHandler(new AjaxAuthenticationSuccessHandler())
                //.failureHandler(new AjaxAuthenticationFailureHandler())
                .permitAll();
        //.and()
        // 配置异常处理
        //.exceptionHandling()
        // 配置权限不足管理,默认为AccessDeniedHandlerImpl
        //.accessDeniedHandler()
        // 配置未认证处理,默认为LoginUrlAuthenticationEntryPoint(跳转到登陆界面)
        //.authenticationEntryPoint();
        return http.build();
    }

    /**
     * cors配置
     *
     * @return CorsConfigurationSource
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "X-Token", "X-Username", "Accept", "X-Requested-With", "X_Requested_With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
