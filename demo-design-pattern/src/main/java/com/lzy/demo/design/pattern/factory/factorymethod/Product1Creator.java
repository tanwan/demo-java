package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

public class Product1Creator implements Creator {

    @Override
    public <T extends Product> T create(Class<T> clazz) {
        return (T) new ConcreteProduct1();
    }
}
