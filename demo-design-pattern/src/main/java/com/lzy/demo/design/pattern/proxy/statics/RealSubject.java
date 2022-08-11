package com.lzy.demo.design.pattern.proxy.statics;


import com.lzy.demo.design.pattern.proxy.Subject;

public class RealSubject implements Subject {

    @Override
    public void operation() {
        System.out.println("operation()");
    }

    @Override
    public void operation(String str) {
        System.out.println("operation():" + str);
    }
}
