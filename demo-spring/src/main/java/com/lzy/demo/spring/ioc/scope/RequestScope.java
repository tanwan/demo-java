/*
 * Created by lzy on 2019-02-19 17:38.
 */
package com.lzy.demo.spring.ioc.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.LongAdder;

/**
 * request
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class RequestScope {
    private static LongAdder instantiationTimes = new LongAdder();

    public RequestScope() {
        instantiationTimes.increment();
        System.out.println("RequestScope Instantiation");
    }

    public int getInstantiationTimes() {
        return instantiationTimes.intValue();
    }
}
