package com.lzy.demo.dubbo.message;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

public class SimpleRequest implements Serializable {

    @NotEmpty
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SimpleRequest{" +
                "request='" + request + '\'' +
                '}';
    }
}
