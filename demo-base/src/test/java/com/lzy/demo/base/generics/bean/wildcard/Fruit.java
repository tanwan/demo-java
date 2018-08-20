/*
 * Created by LZY on 2017/2/27 10:50.
 */
package com.lzy.demo.base.generics.bean.wildcard;

/**
 * 水果父类
 *
 * @author LZY
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
