package com.lzy.demo.security.jwt;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * spring-security配置
 * 如果需要有多个HttpSecurity对象,则可以配置多个WebSecurityConfigurerAdapter实例(多个使用@Order控制顺序)
 *
 * @author LZY
 * @version v1.0
 */
@Profile("jwt")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 在WebSecurityConfiguration#setFilterChainProxySecurityConfigurer()创建出来
     * 为了创建{@link org.springframework.security.web.FilterChainProxy}
     *
     * @param web webSecurity
     * @throws Exception exception
     * @see WebSecurity#performBuild()
     */
    @Override
    public void configure(WebSecurity web) {
        //可以配置一些需要忽略的资源,每个路径对应一个SecurityFilterChain
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 配置HttpSecurity
     *
     * @param http httpSecurity
     * @throws Exception exception
     * @see HttpSecurity#performBuild()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //对应着一个SecurityFilterChain
        http
                //此HttpSecurity的配置只对/login,/logout,/security/**,/secured/**,/jsr250/**,/pre-post/**有效,默认为/**
                .requestMatchers().antMatchers("/login", "/logout", "/security/**", "/secured/**", "/jsr250/**", "/pre-post/**")
                .and()
                // jwt认证的过滤器在原来认证过滤器之前
                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // jwt过滤器在jwt认证过滤器之后
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                // 关闭csrf配置
                .csrf().disable()
                // cors配置,这里如果不配置CorsConfigurationSource的话,则会从spring容器中获取CorsConfigurationSource的实例
                .cors()
                .and()
                //使用ExpressionUrlAuthorizationConfigurer
                .authorizeRequests()
                //任何人都可以访问
                .antMatchers("/security/permit-all").permitAll()
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
                .anyRequest().fullyAuthenticated()
                .and()
                //配置rememberMe
                .rememberMe().userDetailsService(userDetailsService)
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
                .permitAll()
                .and()
                // 配置异常处理
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }
}
