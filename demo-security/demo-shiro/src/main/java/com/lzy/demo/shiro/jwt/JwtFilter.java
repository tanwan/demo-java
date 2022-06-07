package com.lzy.demo.shiro.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Key;

/**
 * jwt过滤器,此filter不能注册为spring bean,否则会自动注册成Filter
 *
 * @author lzy
 * @version v1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 刷新时间
     */
    private static final long REFRESH_TIME = 5 * 60 * 1000;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                return super.executeLogin(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = getAuthzHeader(request);
        // 只要token不为null,则进行尝试登录
        return token != null;
    }

    /**
     * 可以重写这个方法,也可以重写executeLogin方法
     *
     * @see AuthenticatingFilter#executeLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwtToken = getAuthzHeader(servletRequest);
        return new JwtToken(jwtToken.replace(JwtUtils.BEARER_TYPE, ""));
    }


    /**
     * 如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        if (token instanceof JwtToken) {
            User user = (User) subject.getPrincipal();
            // 进行续签,快过期了,重新生成jwt
            if (user.getJwtExpireTime().getTime() - REFRESH_TIME < System.currentTimeMillis()) {
                Key key = new SecretKeySpec(JwtUtils.PASSWORD, SignatureAlgorithm.HS256.getJcaName());
                String jwt = Jwts.builder()
                        // 设置用户名
                        .setSubject(user.getUserName())
                        // 用户id
                        .claim("userId", user.getUserId())
                        // 用户角色
                        .claim("roles", String.join(",", user.getRoleSet()))
                        // 调用权限
                        .claim("permissions", String.join(",", user.getPermissionSet()))
                        // 设置过期时间
                        .setExpiration(JwtUtils.getExpireTime())
                        // 设置密钥
                        .signWith(key)
                        .compact();
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setHeader(JwtUtils.AUTHORIZATION, JwtUtils.BEARER_TYPE + " " + jwt);
            }
        }
        return true;
    }

    /**
     * 需要重写这个方法,否则还会再进行登陆
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String authcHeader = getAuthcScheme() + " realm=\"" + getApplicationName() + "\"";
        httpResponse.setHeader(AUTHENTICATE_HEADER, authcHeader);
        PrintWriter out = response.getWriter();
        out.write("permission denied");
        return false;
    }

}
