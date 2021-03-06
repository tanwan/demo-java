/*
 * Created by lzy on 2019-08-08 16:46.
 */
package com.lzy.demo.design.pattern.singleton;

/**
 * 使用枚举实现单例
 *
 * @author lzy
 * @version v1.0
 */
public enum EnumSingleton {
    /**
     * 单例
     */
    INSTANCE;

    EnumSingleton() {
        System.out.println("EnumSingleton");
    }
}
