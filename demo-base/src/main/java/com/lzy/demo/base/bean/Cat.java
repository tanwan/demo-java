package com.lzy.demo.base.bean;

public class Cat extends Animal {

    @Override
    public void eatFruit(Fruit fruit) {
        System.out.println("cat eat fruit");
    }

    @Override
    public void eatFruit(Apple apple) {
        System.out.println("cat eat apple");
    }

    @Override
    public void eatFruit(Peach peach) {
        System.out.println("cat eat peach");
    }
}
