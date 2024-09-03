package com.lzy.demo.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomScalars {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final GraphQLScalarType DATE_TIME = GraphQLScalarType.newScalar()
            // Scalar的名称
            .name("DateTime")
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
