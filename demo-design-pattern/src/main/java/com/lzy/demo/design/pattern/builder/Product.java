package com.lzy.demo.design.pattern.builder;

public class Product {

    private String component1;
    private String component2;

    public String getComponent1() {
        return component1;
    }

    public void setComponent1(String component1) {
        this.component1 = component1;
    }

    public String getComponent2() {
        return component2;
    }

    public void setComponent2(String component2) {
        this.component2 = component2;
    }

    @Override
    public String toString() {
        return "Product{" +
                "component1='" + component1 + '\'' +
                ", component2='" + component2 + '\'' +
                '}';
    }
}
