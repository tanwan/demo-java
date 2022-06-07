package com.lzy.demo.shiro.jwt;

import java.util.Date;
import java.util.Set;

public class User {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色
     */
    private Set<String> roleSet;

    /**
     * 权限
     */
    private Set<String> permissionSet;

    /**
     * 过期时间
     */

    private Date jwtExpireTime;


    public User(String userName, Integer userId, Set<String> roleSet, Set<String> permissionSet, Date jwtExpireTime) {
        this.userName = userName;
        this.userId = userId;
        this.roleSet = roleSet;
        this.permissionSet = permissionSet;
        this.jwtExpireTime = jwtExpireTime;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public Set<String> getPermissionSet() {
        return permissionSet;
    }

    public Date getJwtExpireTime() {
        return jwtExpireTime;
    }
}
