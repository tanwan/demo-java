package com.lzy.demo.brave.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced
    public RestTemplate ribbonRestTemplate(RestTemplateBuilder builder) {
        // RestTemplate要支持tracing, 需要使用RestTemplateBuilder
        return builder.build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // RestTemplate要支持tracing, 需要使用RestTemplateBuilder
        return builder.build();
    }
}
