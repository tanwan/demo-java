package com.lzy.demo.design.pattern.decorator;

public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        System.out.println("ConcreteDecorator2#operation()");
        super.operation();
    }
}
