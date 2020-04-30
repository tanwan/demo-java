/*
 * Created by lzy on 2020/4/29 1:23 PM.
 */
package com.lzy.demo.test.bean;

/**
 * The type Simple bean.
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleBean {
    /**
     * body
     */
    private String body;


    public SimpleBean(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "body='" + body + '\'' +
                '}';
    }
}
