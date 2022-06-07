package com.lzy.demo.base.bean;

public class Dog extends Animal {

    @Override
    public void eatFruit(Fruit fruit) {
        System.out.println("dog eat fruit");
    }

    @Override
    public void eatFruit(Apple apple) {
        System.out.println("dog eat apple");
    }

    @Override
    public void eatFruit(Peach peach) {
        System.out.println("dog eat peach");
    }
}
