/*
 * Created by lzy on 2019/10/17 6:05 PM.
 */
package com.lzy.demo.security.jwt;

import java.util.Date;

/**
 * The type Jwt utils.
 *
 * @author lzy
 * @version v1.0
 */
public class JwtUtils {

    /**
     * BEARER 类型
     */
    public static final String BEARER_TYPE = "Bearer";

    /**
     * Authorization
     */
    public static final String AUTHORIZATION = "Authorization";


    public static final byte[] PASSWORD = "12345678901234567890123456789012".getBytes();

    /**
     * 过期时间
     */
    private final static int EXPIRE_TIME = 10 * 60 * 1000;


    /**
     * Gets expire time.
     *
     * @return the expire time
     */
    public static Date getExpireTime() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }
}
