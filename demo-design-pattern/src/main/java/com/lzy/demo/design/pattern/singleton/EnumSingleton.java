package com.lzy.demo.design.pattern.singleton;

public enum EnumSingleton {
    INSTANCE;

    EnumSingleton() {
        System.out.println("EnumSingleton");
    }
}
