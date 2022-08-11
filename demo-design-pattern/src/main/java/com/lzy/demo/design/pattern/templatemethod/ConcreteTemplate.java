package com.lzy.demo.design.pattern.templatemethod;

public class ConcreteTemplate extends Template {
    @Override
    protected void primitiveOperation1() {
        System.out.println("operation 1");
    }

    @Override
    protected void primitiveOperation2() {
        System.out.println("operation 2");
    }

    @Override
    protected void hook() {
        System.out.println("hook");
    }
}
