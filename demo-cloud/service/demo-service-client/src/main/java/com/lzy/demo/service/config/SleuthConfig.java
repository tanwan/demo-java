package com.lzy.demo.service.config;

import brave.http.HttpRequestParser;
import org.springframework.cloud.sleuth.instrument.web.HttpClientRequestParser;
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SleuthConfig {

    /**
     * 配置RestTemplate
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate plainRestTemplate() {
        return new RestTemplate();
    }


    /**
     * 默认使用HttpRequestParser.DEFAULT
     *
     * @return the http request parser
     */
    @Bean(name = {HttpClientRequestParser.NAME, HttpServerRequestParser.NAME})
    public HttpRequestParser sleuthHttpServerRequestParser() {
        return (req, context, span) -> {
            HttpRequestParser.DEFAULT.parse(req, context, span);
            String url = req.url();
            if (url != null) {
                span.tag("http.url", url);
            }
        };
    }
}
