package com.lzy.demo.graphql.config;

import com.lzy.demo.graphql.directive.SimpleDirective;
import com.lzy.demo.graphql.scalar.CustomScalars;
import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class SpringBootConfig {


    /**
     * 用来配置scalar,directive
     *
     * @return runtimeWiringConfigurer
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.DateTime)
                .scalar(CustomScalars.COMMON_DATE_TIME)
                .directive("simpleDirective", new SimpleDirective());
    }

}
