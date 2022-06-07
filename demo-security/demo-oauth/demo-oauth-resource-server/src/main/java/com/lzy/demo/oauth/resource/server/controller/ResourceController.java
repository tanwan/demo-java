package com.lzy.demo.oauth.resource.server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class ResourceController {

    @GetMapping("/jwt")
    public String jwt(@AuthenticationPrincipal Jwt jwt) {
        // JwtAuthenticationToken
        JwtAuthenticationToken oAuth2Authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        // oAuth2Authentication.getPrincipal也可以获取到jwt
        Jwt jwtFromJwtAuthenticationToken = (Jwt) oAuth2Authentication.getPrincipal();
        // oAuth2Authentication.getName()获取到的是jwt的subject
        return jwt.getClaims() + ":" + oAuth2Authentication.getName();
    }
}
