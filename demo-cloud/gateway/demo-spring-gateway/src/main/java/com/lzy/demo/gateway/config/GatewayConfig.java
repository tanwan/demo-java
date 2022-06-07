package com.lzy.demo.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayConfig {

    /**
     * 使用java配置,配置路由
     *
     * @param builder the builder
     * @return the route locator
     */
    @Bean
    @Profile("java-config")
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route",
                        r -> r.path("/get/{pathVariable}", "/post/{pathVariable}")
                                .filters(f -> f.addResponseHeader("DEMO-REPONSE-HEAD", "tanwan"))
                                .uri("lb://demo-service-server"))
                .build();
    }
}
