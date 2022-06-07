package com.lzy.demo.design.pattern.singleton;

/**
 * 饿汉式单例模式
 *
 * @author LZY
 * @version v1.0
 */
public final class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
        System.out.println("EagerSingleton()");
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
