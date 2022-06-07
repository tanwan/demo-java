package com.lzy.demo.service.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SimpleRestController {

    /**
     * Gets request.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the request
     */
    @GetMapping("/get/{pathVariable}")
    public Map<String, Object> getRequest(@RequestParam Map<String, Object> request, @PathVariable String pathVariable) {
        request.put("pathVariable", pathVariable);
        return request;
    }

    /**
     * Post request string.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the string
     */
    @PostMapping("/post/{pathVariable}")
    public Map<String, Object> postRequest(@RequestBody Map<String, Object> request, @PathVariable String pathVariable) {
        request.put("pathVariable", pathVariable);
        return request;
    }

    /**
     * Put request string.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the string
     */
    @PostMapping("/put/{pathVariable}")
    public Map<String, Object> putRequest(@RequestBody Map<String, Object> request, @PathVariable String pathVariable) {
        request.put("pathVariable", pathVariable);
        return request;
    }

    /**
     * Delete request string.
     *
     * @param request      the request
     * @param pathVariable the path variable
     * @return the string
     */
    @PostMapping("/delete/{pathVariable}")
    public Map<String, Object> deleteRequest(@RequestBody Map<String, Object> request, @PathVariable String pathVariable) {
        request.put("pathVariable", pathVariable);
        return request;
    }
}
