package com.lzy.demo.spring.ioc.scope;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

@Component
public class SingletonScope {
    private static LongAdder instantiationTimes = new LongAdder();

    public SingletonScope() {
        instantiationTimes.increment();
        System.out.println("SingletonScope Instantiation");
    }

    public int getInstantiationTimes() {
        return instantiationTimes.intValue();
    }
}
