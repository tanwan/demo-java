package com.lzy.demo.design.pattern.decorator;

public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("ConcreteComponent#operation()");
    }
}
