package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 简单工厂
 * 一般是使用静态方法,通过接收的参数的不同来返回不同的对象实例
 * 如果不修改代码,无法进行添加新的产品
 *
 * @author LZY
 * @version v1.0
 */
public class SimpleFactoryCreator {

    /**
     * 通过传入的参数返回不同的对象实例
     * 如果要新增一个产品的话,那么就必须修改此处的代码
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the t
     */
    public static <T extends Product> T create(String type) {
        if (ConcreteProduct1.class.getSimpleName().equals(type)) {
            return (T) new ConcreteProduct1();
        } else if (ConcreteProduct2.class.getSimpleName().equals(type)) {
            return (T) new ConcreteProduct2();
        } else {
            return null;
        }
    }
}
