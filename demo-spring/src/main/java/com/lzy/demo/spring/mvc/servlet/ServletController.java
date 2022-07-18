package com.lzy.demo.spring.mvc.servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServletController {

    @GetMapping("/filter")
    public String filter() {
        return "success";
    }
}
