package com.lzy.demo.brave.service;

import com.lzy.demo.brave.constant.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用feign的service
 * 如果使用fallbackFactory,那么必须指定configuration
 * 如果多个FeignClient使用相同的value值,会报错,这时候有两种解决办法
 * 1. 使用contextId用来当作此bean的bean名(跟fallbackFactory共用会出错)
 * 2. 配置spring.main.allow-bean-definition-overriding为true
 *
 * @author lzy
 * @version v1.0
 */
@FeignClient(value = Constants.DEMO_MICROMETER_BRAVE_SERVER_NAME, path = "/brave")
public interface SimpleFeignService {


    @GetMapping("/port")
    Integer serverPort();


    @GetMapping("/simple-request")
    String simpleRequest();
}
