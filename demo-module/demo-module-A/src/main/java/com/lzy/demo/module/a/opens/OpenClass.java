package com.lzy.demo.module.a.opens;

final class OpenClass {

    private OpenClass() {
        System.out.println("OpenClass");
    }

    private String privateMethod() {
        return "privateMethod";
    }
}
