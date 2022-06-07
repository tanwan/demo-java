package com.lzy.demo.design.pattern.builder;

/**
 * 指导者
 * 将一个复杂对象的构建与它的表示分离,使同样的构建过程可以创建不同的表示
 *
 * @author LZY
 * @version v1.0
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    /**
     * Build product product.
     *
     * @return the product
     */
    public Product buildProduct() {
        builder.buildComponent1();
        builder.buildComponent2();
        return builder.getProduct();
    }
}
