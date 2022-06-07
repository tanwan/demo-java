package com.lzy.demo.test.bean;

public class SimpleBean {

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
