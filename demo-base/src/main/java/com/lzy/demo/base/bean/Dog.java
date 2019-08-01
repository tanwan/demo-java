/*
 * Created by lzy on 17-5-19 下午1:11.
 */
package com.lzy.demo.base.bean;

/**
 * 狗
 *
 * @author lzy
 * @version v1.0
 */
public class Dog extends Animal {

    /**
     * Eat fruit.
     *
     * @param fruit the fruit
     */
    @Override
    public void eatFruit(Fruit fruit) {
        System.out.println("dog eat fruit");
    }

    /**
     * Eat fruit.
     *
     * @param apple the apple
     */
    @Override
    public void eatFruit(Apple apple) {
        System.out.println("dog eat apple");
    }

    /**
     * Eat fruit.
     *
     * @param peach the peach
     */
    @Override
    public void eatFruit(Peach peach) {
        System.out.println("dog eat peach");
    }
}
