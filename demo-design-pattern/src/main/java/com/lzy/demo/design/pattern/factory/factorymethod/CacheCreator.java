package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 充当缓存的工厂
 *
 * @author LZY
 * @version v1.0
 */
public class CacheCreator implements Creator {
    private static ConcurrentHashMap<Class, Product> map = new ConcurrentHashMap<>();

    /**
     * 如果缓存中有该对象直接返回,如果没有则创建实例,然后放入缓存中
     *
     * @param clazz the clazz
     * @return the t
     */
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
