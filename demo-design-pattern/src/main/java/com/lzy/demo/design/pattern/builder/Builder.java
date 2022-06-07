package com.lzy.demo.design.pattern.builder;

/**
 * 建造器接口
 *
 * @author LZY
 * @version v1.0
 */
public interface Builder {
    /**
     * 构建组件1
     */
    void buildComponent1();

    /**
     * 构建组件2.
     */
    void buildComponent2();

    /**
     * 获取复杂类
     *
     * @return the product
     */
    Product getProduct();
}
