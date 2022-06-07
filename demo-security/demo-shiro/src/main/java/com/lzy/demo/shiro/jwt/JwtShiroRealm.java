package com.lzy.demo.shiro.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

public class JwtShiroRealm extends AuthorizingRealm {

    public JwtShiroRealm() {
        setAuthenticationTokenClass(JwtToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(user.getRoleSet());
        info.setStringPermissions(user.getPermissionSet());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(JwtUtils.PASSWORD).build()
                    .parseClaimsJws(jwtToken.getPrincipal().toString())
                    .getBody();
            String username = claims.getSubject();
            Integer userId = (Integer) claims.get("userId");
            Set<String> roleSet = StringUtils.commaDelimitedListToSet(Optional.ofNullable(claims.get("roles")).map(Object::toString).orElse(""));
            Set<String> permissionSet = StringUtils.commaDelimitedListToSet(Optional.ofNullable(claims.get("permissions")).map(Object::toString).orElse(""));

            User user = new User(username, userId, roleSet, permissionSet, claims.getExpiration());
            // 这边其实并没有校验密码
            return new SimpleAuthenticationInfo(user, jwtToken.getCredentials(), getName());
        } catch (Exception e) {
            return null;
        }
    }

}
