package com.lzy.demo.spring.ioc.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

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
