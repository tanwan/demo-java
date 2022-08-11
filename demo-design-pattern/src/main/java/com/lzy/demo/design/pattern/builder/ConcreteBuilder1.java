package com.lzy.demo.design.pattern.builder;

public class ConcreteBuilder1 implements Builder {
    private Product product;

    public ConcreteBuilder1() {
        this.product = new Product();
    }

    @Override
    public void buildComponent1() {
        this.product.setComponent1("ConcreteBuilder1 component1");
    }

    @Override
    public void buildComponent2() {
        this.product.setComponent2("ConcreteBuilder1 component2");
    }

    @Override
    public Product getProduct() {
        return this.product;
    }
}
