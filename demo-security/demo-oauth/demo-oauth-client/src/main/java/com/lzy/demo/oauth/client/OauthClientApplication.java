/*
 * Created by LZY on 2016-09-22.
 */
package com.lzy.demo.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The type Client application.
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootApplication
public class OauthClientApplication {
    /**
     * main函数
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(OauthClientApplication.class, args);
    }
}
