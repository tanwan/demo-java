package com.lzy.demo.design.pattern.factory.abstractfactory;

/**
 * A产品等级,1产品族的实现类
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteProductA1 extends AbstractProductA {
    /**
     * 抽象方法
     */
    @Override
    public void method() {
        System.out.println("ConcreteProductA1");
    }
}
