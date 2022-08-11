package com.lzy.demo.design.pattern.proxy;

public interface Subject {

    default void defaultMethod() {
        System.out.println("defaultMethod");
    }

    void operation();

    void operation(String str);
}
