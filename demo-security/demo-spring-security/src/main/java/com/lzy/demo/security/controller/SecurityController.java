package com.lzy.demo.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/security")
public class SecurityController {

    /**
     * 角色为ROLE_USER才可以访问
     *
     * @return the string
     */
    @GetMapping("/user")
    public String roleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 角色为ROLE_USER才可以访问
     *
     * @return the string
     */
    @GetMapping("/authority")
    public String authority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 角色为ROLE_USER或者ROLE_ADMIN才可以访问
     *
     * @return the string
     */
    @GetMapping("/any-role")
    public String anyRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 角色为ROLE_USER和ROLE_ADMIN才可以访问
     *
     * @return the string
     */
    @GetMapping("/user-admin")
    public String userAndAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 特定ip才可以访问
     *
     * @param request the request
     * @return the string
     */
    @GetMapping("/ip")
    public String ip(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    /**
     * 所有都可以访问
     *
     * @return the string
     */
    @GetMapping("/permit-all")
    public String permitAll() {
        return "permitAll";
    }

    /**
     * 只有匿名用户可以访问
     *
     * @return the string
     */
    @GetMapping("/anonymous")
    public String anonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 任何人都不能访问
     *
     * @return the string
     */
    @GetMapping("/deny-all")
    public String denyAll() {
        return "denyAll";
    }

    /**
     * 只有通过rememberMe cookie登陆的用户可以访问,其它都不能访问
     *
     * @return the string
     */
    @GetMapping("/remember-me")
    public String rememberMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 只要有认证过或者使用rememberMe cookie登陆的用户就可以访问
     *
     * @return the string
     */
    @GetMapping("/authenticated")
    public String authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    /**
     * 必需使用账号密码认证,使用rememberMe cookie登陆的用户无法访问
     *
     * @return the string
     */
    @GetMapping("/fully-authenticated")
    public String fullyAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

}
