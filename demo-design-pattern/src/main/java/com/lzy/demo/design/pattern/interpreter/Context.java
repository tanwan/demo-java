package com.lzy.demo.design.pattern.interpreter;

public class Context {

    private String data;

    public Context(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
