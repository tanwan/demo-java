package com.lzy.demo.base.feature.java8.lamdba.bean;


/**
 * 函数式接口:只能有一个抽象方法,不是只能有一个方法,可以有多个默认方法
 *
 * @param <T> the type parameter
 * @author LZY
 * @version v1.0
 */
@FunctionalInterface
public interface LambdaInterface<T> {

    /**
     * 函数式接口
     *
     * @param x the x
     * @return the string
     */
    T functionalInterface(T x);

    /**
     * 默认方法
     */
    default void defaultMethod() {
        System.out.println("defaultMethod");
    }
}
