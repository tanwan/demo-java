package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 工厂接口
 *
 * @author LZY
 * @version v1.0
 */
public interface Creator {

    /**
     * 产生具体产品
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    <T extends Product> T create(Class<T> clazz);
}
