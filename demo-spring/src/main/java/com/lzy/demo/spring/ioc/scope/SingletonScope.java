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
    private static LongAdder INSTANTIATION_TIMES = new LongAdder();

    public SingletonScope() {
        INSTANTIATION_TIMES.increment();
        System.out.println("SingletonScope Instantiation");
    }

    public int getInstantiationTimes() {
        return INSTANTIATION_TIMES.intValue();
    }
}
