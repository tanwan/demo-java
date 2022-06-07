package com.lzy.demo.graphql.config;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ScalarConfig {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        return COMMON_DATE_TIME;
    }

    public static final GraphQLScalarType COMMON_DATE_TIME = GraphQLScalarType.newScalar()
            // Scalar的名称
            .name("CommonDateTime")
            .coercing(new Coercing() {
                /**
                 * 序列化的时候使用
                 * @param o o
                 * @return object
                 * @throws CoercingSerializeException exception
                 */
                @Override
                public Object serialize(@NotNull Object o) throws CoercingSerializeException {
                    return DATE_TIME_FORMATTER.format((LocalDateTime) o);
                }

                /**
                 * 反序列化的时候使用
                 * query ($request:SimpleRequest){argumentsWithType(request:$request){id integer str }}
                 * 请求参数本身就是json的时候使用此方法
                 *
                 * @param o o
                 * @return object
                 * @throws CoercingParseValueException exception
                 */
                @NotNull
                @Override
                public Object parseValue(@NotNull Object o) throws CoercingParseValueException {
                    return LocalDateTime.parse(o.toString(), DATE_TIME_FORMATTER);
                }

                /**
                 * 反序列化的时候使用
                 * query{argumentsWithType(request:{id:1, str:"str",commonDateTime:"2022-01-01 01:01:01"}){ id integer str }}
                 * 请求参数非json的时候使用
                 *
                 * @param o o
                 * @return object
                 * @throws CoercingParseValueException exception
                 */
                @NotNull
                @Override
                public Object parseLiteral(@NotNull Object o) throws CoercingParseLiteralException {
                    if (o instanceof StringValue) {
                        return LocalDateTime.parse(((StringValue) o).getValue(), DATE_TIME_FORMATTER);
                    }
                    return o.toString();
                }
            }).build();
}
