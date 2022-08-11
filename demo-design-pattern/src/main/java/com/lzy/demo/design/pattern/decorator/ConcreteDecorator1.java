package com.lzy.demo.design.pattern.decorator;

public class ConcreteDecorator1 extends Decorator {

    /**
     * Instantiates a new Concrete decorator 1.
     *
     * @param component the component
     */
    public ConcreteDecorator1(Component component) {
        super(component);
    }

    /**
     * 操作方法
     */
    @Override
    public void operation() {
        System.out.println("ConcreteDecorator1#operation()");
        super.operation();
    }
}
