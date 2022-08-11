package com.lzy.demo.design.pattern.builder;

public class ConcreteBuilder2 implements Builder {
    private Product product;

    public ConcreteBuilder2() {
        this.product = new Product();
    }

    @Override
    public void buildComponent1() {
        this.product.setComponent1("ConcreteBuilder2 component1");
    }

    @Override
    public void buildComponent2() {
        this.product.setComponent2("ConcreteBuilder2 component2");
    }

    @Override
    public Product getProduct() {
        return this.product;
    }
}
