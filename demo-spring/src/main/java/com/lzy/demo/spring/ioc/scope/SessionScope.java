/*
 * Created by lzy on 2019-02-19 17:38.
 */
package com.lzy.demo.spring.ioc.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.LongAdder;

/**
 * session
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SessionScope {
    private static LongAdder INSTANTIATION_TIMES = new LongAdder();

    public SessionScope() {
        INSTANTIATION_TIMES.increment();
        System.out.println("SessionScope Instantiation");
    }

    public int getInstantiationTimes() {
        return INSTANTIATION_TIMES.intValue();
    }
}
