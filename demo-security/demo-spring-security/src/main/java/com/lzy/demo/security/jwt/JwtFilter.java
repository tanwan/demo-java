package com.lzy.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.List;

public class JwtFilter extends GenericFilterBean {

    /**
     * 刷新时间
     */
    private static final long REFRESH_TIME = 5 * 60 * 1000;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwtToken = httpServletRequest.getHeader("authorization");
        if (StringUtils.hasLength(jwtToken)) {
            try {
                // 注意setSigningKey如果参数是String,则会进行base64解码
                Claims claims = Jwts.parser().setSigningKey(JwtUtils.PASSWORD)
                        .parseClaimsJws(jwtToken.replace(JwtUtils.BEARER_TYPE, ""))
                        .getBody();
                // 这边可以使用类似黑名单机制(往redis插入用户,过期时间为jwt的过期时间)来实现注销功能


                // 进行续签,快过期了,重新设置过期时间,或者可以重新生成jwt
                if (claims.getExpiration().getTime() - REFRESH_TIME < System.currentTimeMillis()) {
                    Key key = new SecretKeySpec(JwtUtils.PASSWORD, SignatureAlgorithm.HS256.getJcaName());
                    claims.setExpiration(JwtUtils.getExpireTime());
                    String jwt = Jwts.builder()
                            .signWith(key)
                            .setClaims(claims)
                            .compact();
                    HttpServletResponse resp = (HttpServletResponse) response;
                    resp.setHeader(JwtUtils.AUTHORIZATION, JwtUtils.BEARER_TYPE + " " + jwt);
                }
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || !authentication.isAuthenticated()) {
                    //获取当前登录用户名
                    String username = claims.getSubject();
                    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 过滤
        chain.doFilter(httpServletRequest, response);
    }
}
