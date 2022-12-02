package com.lzy.demo.spring.ioc.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebScopeController {

    @Resource
    private ApplicationContext applicationContext;


    /**
     * Request scope map.
     *
     * @return the map
     */
    @GetMapping("/request-scope")
    public Map<String, Object> requestScope() {
        RequestScope requestScope1 = applicationContext.getBean("requestScope", RequestScope.class);
        RequestScope requestScope2 = applicationContext.getBean("requestScope", RequestScope.class);
        return handleResult(requestScope1 == requestScope2, requestScope1.getInstantiationTimes());
    }

    /**
     * Test filter string.
     *
     * @return the string
     */
    @GetMapping("/session-scope")
    public Map<String, Object> sessionScope() {
        SessionScope sessionScope1 = applicationContext.getBean("sessionScope", SessionScope.class);
        SessionScope sessionScope2 = applicationContext.getBean("sessionScope", SessionScope.class);
        return handleResult(sessionScope1 == sessionScope2, sessionScope1.getInstantiationTimes());
    }

    private Map<String, Object> handleResult(boolean same, Integer instantiationTimes) {
        Map<String, Object> result = new HashMap<>();
        result.put("sameInstance", same);
        result.put("instantiationTimes", instantiationTimes);
        return result;
    }
}
