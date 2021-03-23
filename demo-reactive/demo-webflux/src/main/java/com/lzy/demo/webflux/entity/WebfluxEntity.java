/*
 * Created by lzy on 2019/12/3 10:02 AM.
 */
package com.lzy.demo.webflux.entity;

/**
 * @author lzy
 * @version v1.0
 */
public class WebfluxEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * 消息
     */
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
