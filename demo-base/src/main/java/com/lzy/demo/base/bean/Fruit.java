/*
 * Created by lzy on 17-5-19 下午1:07.
 */
package com.lzy.demo.base.bean;

/**
 * 水果
 *
 * @author lzy
 * @version v1.0
 */
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
