package com.lzy.demo.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * 方法级权限
 * 调用这些方法的时候,需要保证在spring security的管控下,也就是url需要在HttpSecurity的配置下
 *
 * @author LZY
 * @version v1.0
 */
@RestController
public class GlobalMethodController {

    /**
     * 使用@Secured
     * 需要密码认证,rememberMe的不行
     * 使用AuthenticatedVoter处理
     * IS_AUTHENTICATED_REMEMBERED
     * IS_AUTHENTICATED_ANONYMOUSLY
     *
     * @return the string
     */
    @GetMapping("/secured/fully-authenticated")
    @Secured("IS_AUTHENTICATED_FULLY")
    public String securedAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }


    /**
     * 使用@Secured
     * 需要ROLE_USER或者ROLE_ADMIN
     * 使用RoleVoter处理
     *
     * @return the string
     */
    @GetMapping("/secured/role")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String securedRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 使用jsr250
     * 需要ROLE_USER或者ROLE_ADMIN
     * 使用Jsr250Voter处理
     *
     * @return the string
     */
    @GetMapping("/jsr250/role")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String jsr250Role() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 使用@PreAuthorize
     * 需要ROLE_USER
     * 使用PreInvocationAuthorizationAdviceVoter处理
     *
     * @return the string
     */
    @GetMapping("/pre-post/role")
    @PreAuthorize("hasRole('USER')")
    public String prePostRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 先执行方法,然后执行后再进行权限的判断,比如用户只能获取自己的信息
     * returnObject表示返回值,authentication表示已登陆的用户信息(也就是SecurityContextHolder.getContext().getAuthentication()）
     * 使用PreInvocationAuthorizationAdviceVoter处理
     *
     * @return authentication
     */
    @GetMapping("/pre-post/post-authorize")
    @PostAuthorize("returnObject.name == authentication.name")
    public Authentication postAuthorize() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
