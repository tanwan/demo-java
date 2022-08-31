package com.lzy.demo.graphql.config;

import com.lzy.demo.graphql.directive.SimpleDirective;
import com.lzy.demo.graphql.scalar.CustomScalars;
import graphql.kickstart.autoconfigure.tools.SchemaDirective;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KickstartConfig {


    /**
     * 使用DateTimeFormatter.ISO_OFFSET_DATE_TIME
     *
     * @return GraphQLScalarType
     */
    @Bean
    public GraphQLScalarType isoOffsetDateTime() {
        return ExtendedScalars.DateTime;
    }

    /**
     * 使用yyyy-MM-dd HH:mm:ss
     *
     * @return GraphQLScalarType
     */
    @Bean
    public GraphQLScalarType commonDateTime() {
        return CustomScalars.COMMON_DATE_TIME;
    }

    /**
     * 配置自定义directive
     *
     * @return simpleDirective
     */
    @Bean
    public SchemaDirective simpleDirective() {
        return new SchemaDirective("simpleDirective", new SimpleDirective());
    }

}
