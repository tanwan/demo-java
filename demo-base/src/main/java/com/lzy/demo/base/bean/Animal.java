/*
 * Created by lzy on 17-5-19 下午1:08.
 */
package com.lzy.demo.base.bean;

/**
 * 动物
 *
 * @author lzy
 * @version v1.0
 */
public class Animal {
    /**
     * Eat fruit.
     *
     * @param fruit the fruit
     */
    public void eatFruit(Fruit fruit) {
        System.out.println("animal eat fruit");
    }

    /**
     * Eat fruit.
     *
     * @param apple the apple
     */
    public void eatFruit(Apple apple) {
        System.out.println("animal eat apple");
    }

    /**
     * Eat fruit.
     *
     * @param peach the peach
     */
    public void eatFruit(Peach peach) {
        System.out.println("animal eat peach");
    }
}
