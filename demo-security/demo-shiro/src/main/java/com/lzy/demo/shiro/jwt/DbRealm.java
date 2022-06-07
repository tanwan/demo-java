package com.lzy.demo.shiro.jwt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class DbRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.从AuthenticationToken获取出当前登陆用户的用户名
        //2.根据用户名从数据库查找出用户
        //3.如果用户存在,则使用用户的用户名和密码,当前Reaml的名称封装成SimpleAuthenticationInfo
        //4.否则返回null
        if ("lzy".equals(token.getPrincipal())) {
            //用户存在的话,返回SimpleAuthenticationInfo,shiro会判断密码
            return new SimpleAuthenticationInfo(token.getPrincipal(), "123456", getName());
        }
        return null;
    }
}
