package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 一个工厂对应多个产品
 *
 * @author LZY
 * @version v1.0
 */
public class FactoryMethodCreator implements Creator {

    /**
     * 产生具体产品
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    @Override
    public <T extends Product> T create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
