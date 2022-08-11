package com.lzy.demo.design.pattern.flyweight;

public class ConcreteFlyweight implements Flyweight {

    private String name;

    public ConcreteFlyweight(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ConcreteFlyweight{" +
                "name='" + name + '\'' +
                '}';
    }
}
