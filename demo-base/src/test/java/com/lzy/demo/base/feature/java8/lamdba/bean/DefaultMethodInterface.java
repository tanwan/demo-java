package com.lzy.demo.base.feature.java8.lamdba.bean;


/**
 * 其它接口如果继承这个接口,可以重写默认方法
 *
 * @author LZY
 * @version v1.0
 */
public interface DefaultMethodInterface {

    void method(String message);

    default void overrideDefaultMethod() {
        method("DefaultMethodInterface#overrideDefaultMethod()");
    }

    default void defaultMethod() {
        System.out.println("DefaultMethodInterface#defaultMethod()");
    }

    static void staticMethod() {
        System.out.println("DefaultMethodInterface#staticMethod()");
    }
}
