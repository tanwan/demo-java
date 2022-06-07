package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.factory.Product;
import com.lzy.demo.design.pattern.factory.abstractfactory.AbstractFactoryCreator;
import com.lzy.demo.design.pattern.factory.abstractfactory.Product1AbstractFactoryCreator;
import com.lzy.demo.design.pattern.factory.abstractfactory.Product2AbstractFactoryCreator;
import com.lzy.demo.design.pattern.factory.factorymethod.CacheCreator;
import com.lzy.demo.design.pattern.factory.factorymethod.ConcreteProduct1;
import com.lzy.demo.design.pattern.factory.factorymethod.ConcreteProduct2;
import com.lzy.demo.design.pattern.factory.factorymethod.Creator;
import com.lzy.demo.design.pattern.factory.factorymethod.FactoryMethodCreator;
import com.lzy.demo.design.pattern.factory.factorymethod.Product1Creator;
import com.lzy.demo.design.pattern.factory.factorymethod.Product2Creator;
import com.lzy.demo.design.pattern.factory.factorymethod.SimpleFactoryCreator;
import org.junit.jupiter.api.Test;

/**
 * 工厂模式测试
 *
 * @author LZY
 * @version v1.0
 */
public class FactoryTest {

    /**
     * 测试简单工厂
     */
    @Test
    public void testSimpleFactory() {
        Product product1 = SimpleFactoryCreator.create(ConcreteProduct1.class.getSimpleName());
        Product product2 = SimpleFactoryCreator.create(ConcreteProduct2.class.getSimpleName());
        product1.method();
        product2.method();
    }

    /**
     * 测试一个工厂对应多个产品
     */
    @Test
    public void testFactoryMethod() {
        Creator creator = new FactoryMethodCreator();
        Product product1 = creator.create(ConcreteProduct1.class);
        Product product2 = creator.create(ConcreteProduct2.class);
        product1.method();
        product2.method();
    }

    /**
     * 测试一个产品对应一个工厂
     */
    @Test
    public void testOneProductOneFactory() {
        Creator product1Creator = new Product1Creator();
        Creator product2Creator = new Product2Creator();
        Product product1 = product1Creator.create(null);
        Product product2 = product2Creator.create(null);
        product1.method();
        product2.method();
    }

    /**
     * 测试缓存
     */
    @Test
    public void testCache() {
        Creator cacheCreator = new CacheCreator();
        Product product1 = cacheCreator.create(ConcreteProduct1.class);
        Product product2 = cacheCreator.create(ConcreteProduct2.class);
        product1 = cacheCreator.create(ConcreteProduct1.class);
        product2 = cacheCreator.create(ConcreteProduct2.class);
        product1.method();
        product2.method();
    }


    /**
     * 测试抽象工厂
     */
    @Test
    public void testAbstractFactory() {
        AbstractFactoryCreator product1AbstractFactoryCreator = new Product1AbstractFactoryCreator();
        AbstractFactoryCreator product2AbstractFactoryCreator = new Product2AbstractFactoryCreator();
        //产品族1
        Product concreteProductA1 = product1AbstractFactoryCreator.createA();
        Product concreteProductB1 = product1AbstractFactoryCreator.createB();
        //产品族2
        Product concreteProductA2 = product2AbstractFactoryCreator.createA();
        Product concreteProductB2 = product2AbstractFactoryCreator.createB();
        concreteProductA1.method();
        concreteProductB1.method();
        concreteProductA2.method();
        concreteProductB2.method();
    }
}
