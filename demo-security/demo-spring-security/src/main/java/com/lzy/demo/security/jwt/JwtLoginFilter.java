package com.lzy.demo.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.Collection;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {


    protected JwtLoginFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer as = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            as.append(authority.getAuthority())
                    .append(",");
        }
        // 如果这边是使用类似黑名单机制(往redis插入用户,过期时间为jwt的过期时间)来实现注销功能,这边需要清除黑名单
        // 密钥有长度要求,使用HS256的,密钥至少为256bit,也就是长度为32
        Key key = new SecretKeySpec(JwtUtils.PASSWORD, SignatureAlgorithm.HS256.getJcaName());
        String jwt = Jwts.builder()
                // 设置用户名
                .setSubject(authResult.getName())
                // 设置权限
                .claim("authorities", as)
                // 设置过期时间
                .setExpiration(JwtUtils.getExpireTime())
                // 设置密钥
                .signWith(key)
                .compact();
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);

        resp.setHeader(JwtUtils.AUTHORIZATION, JwtUtils.BEARER_TYPE + " " + jwt);
        PrintWriter out = resp.getWriter();
        out.write(jwt);
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out = resp.getWriter();
        out.write("failed");
        out.flush();
        out.close();
    }
}
