package com.lzy.demo.quarkus.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimpleService {

    public void simpleMethod() {
        System.out.println("simpleMethod");
    }
}
