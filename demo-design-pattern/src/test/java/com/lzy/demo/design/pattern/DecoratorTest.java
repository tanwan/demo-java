package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.decorator.Component;
import com.lzy.demo.design.pattern.decorator.ConcreteComponent;
import com.lzy.demo.design.pattern.decorator.ConcreteDecorator1;
import com.lzy.demo.design.pattern.decorator.ConcreteDecorator2;
import org.junit.jupiter.api.Test;

/**
 * 测试装饰模式
 *
 * @author LZY
 * @version v1.0
 */
public class DecoratorTest {

    /**
     * 测试装饰模式,先进行具体装饰者1装饰,再进行具体装饰者2装饰,先执行具体装饰者2的方法,再执行具体装饰者1的方法
     */
    @Test
    public void testDecorator1() {
        //原始的组件
        Component component = new ConcreteComponent();
        //使用ConcreteDecorator1装饰
        component = new ConcreteDecorator1(component);
        //使用ConcreteDecorator2装饰
        component = new ConcreteDecorator2(component);
        component.operation();
    }

    /**
     * 测试装饰模式,先进行具体装饰者2装饰,再进行具体装饰者1装饰,先执行具体装饰者1的方法,再执行具体装饰者2的方法
     */
    @Test
    public void testDecorator2() {
        Component component = new ConcreteComponent();
        //使用ConcreteDecorator2装饰
        component = new ConcreteDecorator2(component);
        //使用ConcreteDecorator1装饰
        component = new ConcreteDecorator1(component);
        component.operation();
    }

    /**
     * 测试装饰模式
     */
    @Test
    public void testDecorator3() {
        Component component = new ConcreteDecorator1(new ConcreteDecorator2(new ConcreteComponent()));
        component.operation();
    }
}
