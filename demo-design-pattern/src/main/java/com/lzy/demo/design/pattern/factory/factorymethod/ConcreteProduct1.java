package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 具体产品1
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteProduct1 implements Product {
    @Override
    public void method() {
        System.out.println("ConcreteProduct1");
    }
}
