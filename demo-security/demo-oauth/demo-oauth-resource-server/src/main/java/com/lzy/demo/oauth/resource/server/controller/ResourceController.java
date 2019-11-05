/*
 * Created by LZY on 2017/4/25 23:07.
 */


package com.lzy.demo.oauth.resource.server.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Resource controller.
 *
 * @author LZY
 * @version v1.0
 */
@RestController
@RequestMapping("/oauth2")
public class ResourceController {

    /**
     * 只能使用oauth2访问
     *
     * @return the string
     */
    @GetMapping("/only-oauth2")
    public String oauth2() {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        // 获取客户端的信息 oAuth2Authentication.getOAuth2Request()
        // 获取用户的信息(如果是客户端模式的话,这个值为null) oAuth2Authentication.getUserAuthentication()
        return oAuth2Authentication.getOAuth2Request().getClientId() + ":" + oAuth2Authentication.getName();
    }

    /**
     * 既可以使用oauth,也可以使用spring-security访问
     *
     * @return the string
     */
    @GetMapping("/oauth-or-security")
    public String oauthOrSecurity() {
        return SecurityContextHolder.getContext().getAuthentication().getClass().getSimpleName();
    }
}
