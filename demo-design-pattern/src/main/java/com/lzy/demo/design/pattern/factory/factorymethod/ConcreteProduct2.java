package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

public class ConcreteProduct2 implements Product {
    @Override
    public void method() {
        System.out.println("ConcreteProduct2");
    }
}
