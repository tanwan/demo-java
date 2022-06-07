package com.lzy.demo.dubbo.api;

import com.lzy.demo.dubbo.message.SimpleRequest;
import com.lzy.demo.dubbo.message.SimpleResponse;

public interface SimpleService {

    /**
     * Simple service simple response.
     *
     * @param request the request
     * @return the simple response
     */
    SimpleResponse simpleService(SimpleRequest request);
}
