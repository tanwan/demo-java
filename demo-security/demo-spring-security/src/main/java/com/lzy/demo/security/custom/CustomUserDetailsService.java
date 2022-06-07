package com.lzy.demo.security.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * InitializeUserDetailsBeanManagerConfigurer.InitializeUserDetailsManagerConfigurer#configure()
 * 这个方法会扫描UserDetailsService的实例,然后使用DaoAuthenticationProvider添加到AuthenticationManagerBuilder#authenticationProviders
 * AuthenticationManagerBuilder#performBuild()把authenticationProviders添加到ProviderManager
 *
 * @author LZY
 * @version v1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 先根据用户名从数据库获取出用户,不存在则抛出UsernameNotFoundException的异常
        // 2. 从数据库获取用户的权限(也就是角色),然后封装成List<GrantedAuthority>

        // 角色需要以ROLE_开头
        List<GrantedAuthority> auths = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        String password = passwordEncoder.encode("123456");
        return new User("lzy", password, true, true, true, true, auths);
    }
}
