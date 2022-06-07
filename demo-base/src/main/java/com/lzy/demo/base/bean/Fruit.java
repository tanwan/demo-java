package com.lzy.demo.base.bean;

public class Fruit implements Comparable<Fruit> {
    private String name;


    public Fruit(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Fruit o) {
        return this.name.compareTo(o.getName());
    }
}
