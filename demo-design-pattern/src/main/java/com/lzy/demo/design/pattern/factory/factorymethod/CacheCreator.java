package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

import java.util.concurrent.ConcurrentHashMap;

public class CacheCreator implements Creator {
    private static ConcurrentHashMap<Class, Product> map = new ConcurrentHashMap<>();

    @Override
    public <T extends Product> T create(Class<T> clazz) {
        T product = (T) map.get(clazz);
        if (product == null) {
            try {
                System.out.println(clazz + "new Instance");
                product = clazz.newInstance();
                map.put(clazz, product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return product;
    }
}
