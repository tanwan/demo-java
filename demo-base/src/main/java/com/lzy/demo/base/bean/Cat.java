/*
 * Created by lzy on 17-5-19 下午1:10.
 */
package com.lzy.demo.base.bean;

/**
 * 猫
 *
 * @author lzy
 * @version v1.0
 */
public class Cat extends Animal {

    /**
     * Eat fruit.
     *
     * @param fruit the fruit
     */
    @Override
    public void eatFruit(Fruit fruit) {
        System.out.println("cat eat fruit");
    }

    /**
     * Eat fruit.
     *
     * @param apple the apple
     */
    @Override
    public void eatFruit(Apple apple) {
        System.out.println("cat eat apple");
    }

    /**
     * Eat fruit.
     *
     * @param peach the peach
     */
    @Override
    public void eatFruit(Peach peach) {
        System.out.println("cat eat peach");
    }
}
