package com.lzy.demo.graphql.config;

import com.lzy.demo.graphql.directive.SimpleDirective;
import com.lzy.demo.graphql.scalar.CustomScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    /**
     * 用来配置scalar,directive
     *
     * @return runtimeWiringConfigurer
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(CustomScalars.DATE_TIME)
                .directive("simpleDirective", new SimpleDirective());
    }

}
