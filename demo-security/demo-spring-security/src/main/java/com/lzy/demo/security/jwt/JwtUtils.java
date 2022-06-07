package com.lzy.demo.security.jwt;

import java.util.Date;

public class JwtUtils {

    public static final String BEARER_TYPE = "Bearer";

    public static final String AUTHORIZATION = "Authorization";


    public static final byte[] PASSWORD = "12345678901234567890123456789012".getBytes();

    /**
     * 过期时间
     */
    private static final int EXPIRE_TIME = 10 * 60 * 1000;


    /**
     * Gets expire time.
     *
     * @return the expire time
     */
    public static Date getExpireTime() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }
}
