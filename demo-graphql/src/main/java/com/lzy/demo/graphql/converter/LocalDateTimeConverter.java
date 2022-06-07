package com.lzy.demo.graphql.converter;

import com.expediagroup.graphql.client.converter.ScalarConverter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 使用com.expediagroup.graphql生成的kotlin代码需要使用此类
 *
 * @author lzy
 * @version v1.0
 */
public class LocalDateTimeConverter implements ScalarConverter<LocalDateTime> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @NotNull
    @Override
    public Object toJson(LocalDateTime offsetDateTime) {
        return DATE_TIME_FORMATTER.format(offsetDateTime);
    }

    @Override
    public LocalDateTime toScalar(@NotNull Object o) {
        return LocalDateTime.parse(o.toString(), DATE_TIME_FORMATTER);
    }
}
