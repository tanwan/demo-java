/*
 * Created by LZY on 2017/7/2 14:12.
 */
package com.lzy.demo.design.pattern.factory.abstractfactory;

/**
 * B产品等级,1产品族的实现类
 * @author LZY
 * @version v1.0
 */
public class ConcreteProductB1 extends ProductB {
    /**
     * 抽象方法
     */
    @Override
    public void method() {
        System.out.println("ConcreteProductB1");
    }
}
