package com.lzy.demo.dubbo.server;

import com.lzy.demo.dubbo.api.SimpleService;
import com.lzy.demo.dubbo.message.SimpleRequest;
import com.lzy.demo.dubbo.message.SimpleResponse;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 服务提供者org.apache.dubbo.config.annotation.DubboService注解的属性同dubbo:service
 *
 * @author lzy
 * @version v1.0
 */
@DubboService
public class SimpleServiceDubbompl implements SimpleService {

    @Override
    public SimpleResponse simpleService(SimpleRequest request) {
        System.out.println(request);
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setResponse("hello world");
        return simpleResponse;
    }
}
