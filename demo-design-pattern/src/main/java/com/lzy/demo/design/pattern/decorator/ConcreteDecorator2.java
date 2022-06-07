package com.lzy.demo.design.pattern.decorator;

/**
 * 具体装饰者2
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteDecorator2 extends Decorator {

    /**
     * Instantiates a new Concrete decorator 2.
     *
     * @param component the component
     */
    public ConcreteDecorator2(Component component) {
        super(component);
    }

    /**
     * 操作方法
     */
    @Override
    public void operation() {
        System.out.println("ConcreteDecorator2#operation()");
        super.operation();
    }
}
