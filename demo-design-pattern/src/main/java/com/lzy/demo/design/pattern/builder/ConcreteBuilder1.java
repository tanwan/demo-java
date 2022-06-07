package com.lzy.demo.design.pattern.builder;

/**
 * 建造者1
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteBuilder1 implements Builder {
    private Product product;

    public ConcreteBuilder1() {
        this.product = new Product();
    }

    /**
     * 构建组件1
     */
    @Override
    public void buildComponent1() {
        this.product.setComponent1("ConcreteBuilder1 component1");
    }

    /**
     * 构建组件2.
     */
    @Override
    public void buildComponent2() {
        this.product.setComponent2("ConcreteBuilder1 component2");
    }

    /**
     * 获取复杂类
     *
     * @return the product
     */
    @Override
    public Product getProduct() {
        return this.product;
    }
}
