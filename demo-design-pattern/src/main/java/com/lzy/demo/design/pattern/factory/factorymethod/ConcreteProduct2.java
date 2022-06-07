package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 具体产品2
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteProduct2 implements Product {
    @Override
    public void method() {
        System.out.println("ConcreteProduct2");
    }
}
