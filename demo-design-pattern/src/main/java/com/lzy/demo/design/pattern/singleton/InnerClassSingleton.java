/*
 * Created by LZY on 2017/5/27 16:29.
 */
package com.lzy.demo.design.pattern.singleton;

/**
 * 内部类单例
 *
 * @author LZY
 * @version v1.0
 */
public final class InnerClassSingleton {

    private InnerClassSingleton() {
        System.out.println("InnerClassSingleton()");
    }

    /**
     * 类只会被加载一次,因此是线程安全的
     * 只有使用到这个类的时候才会实例化instance
     */
    private static class InnerClassHolder {
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static InnerClassSingleton getInstance() {
        return InnerClassHolder.INSTANCE;
    }
}
