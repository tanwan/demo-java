package com.lzy.demo.shiro.controller;

import com.lzy.demo.shiro.config.ShiroConfiguration;
import com.lzy.demo.shiro.jwt.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.authz.aop.GuestAnnotationHandler;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.apache.shiro.authz.aop.UserAnnotationHandler;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class ShiroController {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Instantiates a new Shiro controller.
     */
    public ShiroController() {
        // 线程为非web线程创建
        executorService.submit(() -> {
        });
    }

    /**
     * 所有人都可以访问
     *
     * @return the string
     * @see AnonymousFilter
     */
    @GetMapping("/anonymous")
    @ResponseBody
    public String anonymous() {
        return Optional.ofNullable(SecurityUtils.getSubject().getPrincipal())
                .map(Object::toString).orElse("anonymous");
    }

    /**
     * rememberMe登陆和直接登陆都可以访问
     *
     * @return the string
     * @see UserFilter
     */
    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 只有直接登陆的用户才可以访问,rememberMe登陆也不能访问
     *
     * @return the string
     * @see FormAuthenticationFilter
     */
    @GetMapping("/authc")
    @ResponseBody
    public String authc() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 当前用户需要有指定的所有角色才可以访问
     *
     * @return the string
     * @see RolesAuthorizationFilter
     */
    @GetMapping("/roles")
    @ResponseBody
    public String roles() {
        return "roles";
    }

    /**
     * 当前用户需要有指定的权限才可以访问
     *
     * @return the string
     * @see PermissionsAuthorizationFilter
     */
    @GetMapping("/perms")
    @ResponseBody
    public String perms() {
        return "perms";
    }

    /**
     * rest风格的权限配置,Get请求需要rest:read,Post请求需要rest:create,Put请求需要rest:update,Delete请求需要rest:delete
     *
     * @return the string
     * @see HttpMethodPermissionFilter
     */
    @RequestMapping("/rest")
    @ResponseBody
    public String rest() {
        return "rest";
    }

    /**
     * 只有直接登陆的用户才可以访问,rememberMe登陆也不能访问
     *
     * @return the string
     * @see AuthenticatedAnnotationHandler
     */
    @GetMapping("/requires-authentication")
    @ResponseBody
    @RequiresAuthentication()
    public String requiresAuthentication() {
        return "requiresAuthentication";
    }

    /**
     * 未登陆的才能访问
     *
     * @return the string
     * @see GuestAnnotationHandler
     */
    @GetMapping("/requires-guest")
    @ResponseBody
    @RequiresGuest()
    public String requiresGuest() {
        return "requiresGuest";
    }

    /**
     * 当前用户需要有指定的权限才可以访问
     *
     * @return the string
     * @see PermissionAnnotationHandler
     */
    @GetMapping("/requires-permissions")
    @ResponseBody
    @RequiresPermissions("perms:read")
    public String requiresPermissions() {
        return "requiresPermissions";
    }

    /**
     * 当前用户需要有指定的角色才可以访问,可以使用and,也可以使用or
     *
     * @return the string
     * @see RoleAnnotationHandler
     */
    @GetMapping("/requires-roles")
    @ResponseBody
    @RequiresRoles("user1")
    public String requiresRoles() {
        return "requiresRoles";
    }

    /**
     * rememberMe登陆和直接登陆都可以访问
     *
     * @return the string
     * @see UserAnnotationHandler
     */
    @GetMapping("/requires-user")
    @ResponseBody
    @RequiresUser()
    public String requiresUser() {
        return "requiresUser";
    }

    /**
     * 使用FormAuthenticationFilter和shiroFilterFactoryBean.setLoginUrl("/login")进行登录的话
     * 返回页面使用的是Get /login,登录处理是使用Post /login
     * 因此这边需要接收Get请求,然后转到登录页面
     * 对于Post /login,如果登录成功,则会直接跳转到成功页面,而如果失败的话,还会继续执行Post /login
     * 因此,这边也需要接收Post请求,来处理登录失败的情况
     *
     * @return the string
     */
    @RequestMapping(value = "/login")
    public String login() {
        //判断是否已经登录 或 是否已经记住我
        if (SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered()) {
            return "/index.html";
        } else {
            // 这边只能使用重定向,因此如果是Post请求过来的话,使用forward无法跳转到html页面,会报405错误
            return "redirect:/login-page.html";
        }
    }

    /**
     * ajax登陆
     *
     * @param username the username
     * @param password the password
     * @return the string
     */
    @PostMapping(value = "/ajax-login")
    @ResponseBody
    public String ajaxLogin(String username, String password) {
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        return "ajax-login success";
    }


    /**
     * 获取用户
     *
     * @return the user
     * @see ShiroConfiguration#securityManager() ShiroConfiguration#securityManager()
     */
    @GetMapping("/get-user")
    @ResponseBody
    @RequiresAuthentication()
    public String getUser() {
        new Thread(() -> {
            // 新线程获取的是父线程的用户名
            System.out.println("父线程:" + SecurityUtils.getSubject().getPrincipal());
        }).start();

        executorService.submit(() -> {
            // 线程是其它线程创建的,默认情况下会抛出UnavailableSecurityManagerException的异常
            // 如果为SecurityUtils设置安全管理器,就不会抛出异常
            try {
                System.out.println("其它线程:" + SecurityUtils.getSubject().getPrincipal());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return SecurityUtils.getSubject().getPrincipal().toString();
    }


    /**
     * 权限不足
     *
     * @return the string
     */
    @GetMapping("/forbidden")
    @ResponseBody
    @RequiresPermissions("forbidden")
    public String forbidden() {
        return "";
    }


    /**
     * jwt登陆
     *
     * @param response the response
     * @param username the username
     * @param password the password
     * @return the string
     */
    @PostMapping(value = "/jwt-login")
    @ResponseBody
    public String jwtLogin(HttpServletResponse response, String username, String password) {
        // 如果这边是使用类似黑名单机制(往redis插入用户,过期时间为jwt的过期时间)来实现注销功能,这边需要清除黑名单

        // 进行这边使用DbRealm进行登录,也可以是直接从数据库获取数据,然后进行校验
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(token);

        // 密钥有长度要求,使用HS256的,密钥至少为256bit,也就是长度为32
        Key key = new SecretKeySpec(JwtUtils.PASSWORD, SignatureAlgorithm.HS256.getJcaName());
        // 这边可以获取用户的权限,然后返回保存的jwt中
        String jwt = Jwts.builder()
                // 设置用户名
                .setSubject(username)
                // 用户id
                .claim("userId", 23)
                // 用户角色
                .claim("roles", "user")
                // 调用权限
                .claim("permissions", "perms:read,rest:read")
                // 设置过期时间
                .setExpiration(JwtUtils.getExpireTime())
                // 设置密钥
                .signWith(key)
                .compact();
        response.setHeader(JwtUtils.AUTHORIZATION, JwtUtils.BEARER_TYPE + " " + jwt);
        return jwt;
    }
}
