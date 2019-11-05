/*
 * Created by LZY on 2016-09-22.
 */
package com.lzy.demo.oauth.authorization.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 授权服务器,可以单独一个项目,也可以和资源服务器在同一个项目
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootApplication
public class AuthorizationServerApplication {

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }
}
