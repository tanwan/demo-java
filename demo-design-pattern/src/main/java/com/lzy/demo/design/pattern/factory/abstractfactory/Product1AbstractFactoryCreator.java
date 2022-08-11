package com.lzy.demo.design.pattern.factory.abstractfactory;

import com.lzy.demo.design.pattern.factory.Product;

public class Product1AbstractFactoryCreator implements AbstractFactoryCreator {
    @Override
    public <T extends Product> T createA() {
        return (T) new ConcreteProductA1();
    }

    @Override
    public <T extends Product> T createB() {
        return (T) new ConcreteProductB1();
    }
}
