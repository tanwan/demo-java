package com.lzy.demo.design.pattern.singleton;

public final class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
        System.out.println("EagerSingleton()");
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
