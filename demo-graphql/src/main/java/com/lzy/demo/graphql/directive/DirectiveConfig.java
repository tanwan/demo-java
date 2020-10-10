package com.lzy.demo.graphql.directive;

import graphql.kickstart.tools.boot.SchemaDirective;
import graphql.schema.idl.SchemaDirectiveWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Directive config.
 *
 * @author lzy
 * @version v1.0
 */
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
