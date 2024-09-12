package com.lzy.demo.service.service;

import com.lzy.demo.service.constant.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
@FeignClient(value = Constants.DEMO_SERVICE_SERVER_NAME)
public interface SimpleFeignService {

    /**
     * Server port integer.
     *
     * @return the integer
     */
    @GetMapping("/port")
    Integer serverPort();

    /**
     * Gets request.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the request
     */
    @GetMapping("/get/{pathVariable}")
    Map<String, Object> getRequest(@RequestParam Map<String, Object> request, @PathVariable("pathVariable") String pathVariable);

    /**
     * Gets request.
     *
     * @param requestParam the request param
     * @param pathVariable the path variable
     * @return the request
     */
    @GetMapping("/get/{pathVariable}")
    Map<String, Object> getRequest(@RequestParam("requestParam") String requestParam, @PathVariable("pathVariable") String pathVariable);


    /**
     * Post request string.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the string
     */
    @PostMapping("/post/{pathVariable}")
    Map<String, Object> postRequest(@RequestBody Map<String, Object> request, @PathVariable("pathVariable") String pathVariable);

    /**
     * Put request string.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the string
     */
    @PostMapping("/put/{pathVariable}")
    Map<String, Object> putRequest(@RequestBody Map<String, Object> request, @PathVariable("pathVariable") String pathVariable);

    /**
     * Delete request string.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the string
     */
    @PostMapping("/delete/{pathVariable}")
    Map<String, Object> deleteRequest(@RequestBody Map<String, Object> request, @PathVariable("pathVariable") String pathVariable);
}
