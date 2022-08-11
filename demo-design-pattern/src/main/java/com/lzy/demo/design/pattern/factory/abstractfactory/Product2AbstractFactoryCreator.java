package com.lzy.demo.design.pattern.factory.abstractfactory;

import com.lzy.demo.design.pattern.factory.Product;

public class Product2AbstractFactoryCreator implements AbstractFactoryCreator {

    @Override
    public <T extends Product> T createA() {
        return (T) new ConcreteProductA2();
    }

    @Override
    public <T extends Product> T createB() {
        return (T) new ConcreteProductB2();
    }
}
