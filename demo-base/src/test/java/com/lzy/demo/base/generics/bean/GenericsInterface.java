/*
 * Created by LZY on 12/17/2016 13:04.
 */
package com.lzy.demo.base.generics.bean;

/**
 * 泛型接口
 *
 * @param <T> the type parameter
 * @author LZY
 * @version v1.0
 */
public interface GenericsInterface<T> {
    /**
     * Print.
     *
     * @param t the t
     */
    void print(T t);
}
