package com.lzy.demo.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置
 *
 * @author LZY
 * @version v1.0
 */
@Profile("default")
@Configuration
public class ShiroConfiguration {

    /**
     * 安全管理器
     *
     * @return the security manager
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        ShiroRealm shiroRealm = new ShiroRealm();
        // 可以为Realm设置CredentialsMatcher,用来对密码进行hash,比如md5 sha1
        // shiroRealm.setCredentialsMatcher();
        securityManager.setRealm(shiroRealm);
        //使用内存缓存,可以自定义使用其它缓存(实现Cache和CacheManager),使用redis的话,则用RedisCacheManager(依赖shiro-redis)
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());

        // 为SecurityUtils设置默认的安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean的配置
     *
     * @param securityManager the security manager
     * @return the shiro filter factory bean
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录页面和登录处理,登录页面使用Get请求(因此需要在controller对Get /login进行跳转到登录而),登录处理使用Post请求,同时,如果想使用FormAuthenticationFilter进行登录处理的话,需要把/login设置为authc
        // FormAuthenticationFilter登录失败的处理是个坑,并不会直接回到登录界面,而是继续执行Post /login,因此需要在controller对Post /login进行跳转到登录页
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的地址
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        // 权限不足的url(非注解式的权限不足会重定向到这里,注解式的使用全局异常捕获)
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");
        // 使用setFilters可以自定义过滤器,如自定义FormAuthenticationFilter
        //shiroFilterFactoryBean.setFilters();

        // 过滤器名称和过滤器定义(包括过滤器的类型和配置)的映射关系
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 使用AuthenticatingFilter进行登录的话,/login需要使用authc权限,这样FormAuthenticationFilter才能进行登录处理
        filterChainDefinitionMap.put("/login", DefaultFilter.authc.name());
        // 所有人都可以访问
        filterChainDefinitionMap.put("/anonymous/**", DefaultFilter.anon.name());
        // rememberMe登录和直接登录都可以访问
        filterChainDefinitionMap.put("/user", DefaultFilter.user.name());
        // 只有直接登录的用户才可以访问,rememberMe登录也不能访问
        filterChainDefinitionMap.put("/authc", DefaultFilter.authc.name());
        // 当前用户需要有所有指定的角色才可以访问,多个角色使用逗号分隔
        filterChainDefinitionMap.put("/roles", DefaultFilter.roles.name() + "[user]");
        // 当前用户需要有指定的权限才可以访问
        filterChainDefinitionMap.put("/perms", DefaultFilter.perms.name() + "[perms:read]");
        // rest风格的权限配置,Get请求需要rest:read,Post请求需要rest:create,Put请求需要rest:update,Delete请求需要rest:delete
        filterChainDefinitionMap.put("/rest", DefaultFilter.rest.name() + "[rest]");
        // 配置登出的过滤器
        filterChainDefinitionMap.put("/logout", DefaultFilter.logout.name());
        // ajax登录需要匿名
        filterChainDefinitionMap.put("/ajax-login", DefaultFilter.anon.name());
        // 如果所有的请求都拦截的话,注意放开一些静态资源,比如登陆界面
        filterChainDefinitionMap.put("/*.html", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/*.ico", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/**", DefaultFilter.authc.name());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 如果要使用shiro注解,需要配置AuthorizationAttributeSourceAdvisor和DefaultAdvisorAutoProxyCreator
     *
     * @param securityManager the security manager
     * @return the authorization attribute source advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 如果要使用shiro注解,需要配置AuthorizationAttributeSourceAdvisor和DefaultAdvisorAutoProxyCreator
     *
     * @return the default advisor auto proxy creator
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
