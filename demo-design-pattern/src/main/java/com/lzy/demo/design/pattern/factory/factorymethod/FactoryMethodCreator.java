package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

public class FactoryMethodCreator implements Creator {

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
