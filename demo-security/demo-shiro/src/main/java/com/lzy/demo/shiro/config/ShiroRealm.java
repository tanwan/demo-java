package com.lzy.demo.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的Realm,用来获取用户和用户的权限
 * @author LZY
 * @version v1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 获取用户的权限
     * @param principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取当前登陆的用户名
        //2.根据用户名查找出当前用户的角色
        //3.查找出当前用户拥有的权限
        //4.把角色和权限设置到AuthorizationInfo
        super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<>();
        roleSet.add("user");
        info.setRoles(roleSet);
        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("perms:read");
        permissionSet.add("rest:read");
        info.setStringPermissions(permissionSet);
        return info;
    }

    /**
     * 认证
     */
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
