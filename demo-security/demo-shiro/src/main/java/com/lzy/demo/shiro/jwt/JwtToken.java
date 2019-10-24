/*
 * Created by lzy on 2019/10/22 3:47 PM.
 */
package com.lzy.demo.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * The type Jwt token.
 *
 * @author lzy
 * @version v1.0
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
