/*
 * Created by lzy on 2019-02-19 17:25.
 */
package com.lzy.demo.spring.ioc.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

/**
 * Prototype
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeScope {
    private static LongAdder instantiationTimes = new LongAdder();

    public PrototypeScope() {
        instantiationTimes.increment();
        System.out.println("PrototypeScope Instantiation");
    }

    public int getInstantiationTimes() {
        return instantiationTimes.intValue();
    }

}
