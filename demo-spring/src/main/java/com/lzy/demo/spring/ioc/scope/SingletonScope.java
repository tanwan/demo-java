/*
 * Created by lzy on 2019-02-19 17:19.
 */
package com.lzy.demo.spring.ioc.scope;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

/**
 * singleton
 *
 * @author lzy
 * @version v1.0
 */
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
