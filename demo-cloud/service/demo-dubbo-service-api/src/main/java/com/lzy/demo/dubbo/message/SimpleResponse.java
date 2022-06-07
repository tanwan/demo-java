package com.lzy.demo.dubbo.message;

import java.io.Serializable;

public class SimpleResponse implements Serializable {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}
