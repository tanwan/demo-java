package com.lzy.demo.graphql.directive;

import graphql.kickstart.autoconfigure.tools.SchemaDirective;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectiveConfig {


    /**
     * Simple directive schema directive.
     *
     * @return the schema directive
     */
    @Bean
    public SchemaDirective simpleDirective() {
        return new SchemaDirective("simpleDirective", new SimpleDirective());
    }
}
