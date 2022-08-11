package com.lzy.demo.design.pattern.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    private static final Map<String, Flyweight> FLYWEIGHT_MAP = new HashMap<>();


    public static Flyweight getFlyweight(String name) {
        // 本质是重用现有的对象
        return FLYWEIGHT_MAP.computeIfAbsent(name, k -> {
            System.out.println("create ConcreteFlyweight: " + k);
            return new ConcreteFlyweight(k);
        });
    }
}
