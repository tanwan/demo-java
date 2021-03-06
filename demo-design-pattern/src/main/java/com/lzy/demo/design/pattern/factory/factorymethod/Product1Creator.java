/*
 * Created by LZY on 2017/7/1 22:41.
 */
package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 一个产品对应一个工厂
 * 产品1的工厂
 *
 * @author LZY
 * @version v1.0
 */
public class Product1Creator implements Creator {

    /**
     * 创建产品1
     *
     * @param <T>   the type parameter
     * @param clazz 一个产品对应一个工厂并不需要这个参数
     * @return the t
     */
    @Override
    public <T extends Product> T create(Class<T> clazz) {
        return (T) new ConcreteProduct1();
    }
}
