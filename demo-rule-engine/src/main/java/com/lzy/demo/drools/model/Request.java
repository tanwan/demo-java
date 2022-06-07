package com.lzy.demo.drools.model;

public class Request {

    private Integer integer;

    private String str;

    private String result;

    public Request(Integer integer, String str) {
        this.integer = integer;
        this.str = str;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "integer=" + integer +
                ", str='" + str + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
