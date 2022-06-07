package com.lzy.demo.design.pattern.factory.abstractfactory;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 产品族2的生成工厂类
 * @author LZY
 * @version v1.0
 */
public class Product2AbstractFactoryCreator implements AbstractFactoryCreator {
    /**
     * A产品等级的生成方法
     *
     * @return the t
     */
    @Override
    public <T extends Product> T createA() {
        return (T) new ConcreteProductA2();
    }

    /**
     * B产品等级的生成方法
     *
     * @return the t
     */
    @Override
    public <T extends Product> T createB() {
        return (T) new ConcreteProductB2();
    }
}
