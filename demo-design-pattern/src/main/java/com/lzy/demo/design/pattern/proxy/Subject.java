package com.lzy.demo.design.pattern.proxy;

/**
 * 代理模式的接口
 *
 * @author LZY
 * @version v1.0
 */
public interface Subject {
    /**
     * 默认方法
     */
    default void defaultMethod() {
        System.out.println("defaultMethod");
    }

    /**
     * 操作
     */
    void operation();

    /**
     * 操作
     *
     * @param str the str
     */
    void operation(String str);
}
