package com.lzy.demo.design.pattern.prototype;

/**
 * 原型的接口
 *
 * @author LZY
 * @version v1.0
 */
public interface Prototype {

    /**
     * 拷贝的方法
     *
     * @param <T> the type parameter
     * @return the prototype
     */
    <T extends Prototype> T copy();
}
