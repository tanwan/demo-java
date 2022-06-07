package com.lzy.demo.dubbo.server;

import com.lzy.demo.dubbo.api.SimpleService;
import com.lzy.demo.dubbo.message.SimpleRequest;
import com.lzy.demo.dubbo.message.SimpleResponse;
import org.springframework.stereotype.Service;

/**
 * 服务提供者
 *
 * @author lzy
 * @version v1.0
 */
@Service
public class SimpleServiceSpringImpl implements SimpleService {

    @Override
    public SimpleResponse simpleService(SimpleRequest request) {
        System.out.println(request);
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setResponse("hello world");
        return simpleResponse;
    }
}
