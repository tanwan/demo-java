package com.lzy.demo.graphql.converter;

import com.expediagroup.graphql.client.converter.ScalarConverter;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 使用com.expediagroup.graphql生成的kotlin代码需要使用此类
 *
 * @author lzy
 * @version v1.0
 */
public class DateTimeConverter implements ScalarConverter<OffsetDateTime> {

    @NotNull
    @Override
    public Object toJson(OffsetDateTime localDateTime) {
        return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    }

    @Override
    public OffsetDateTime toScalar(@NotNull Object o) {
        return OffsetDateTime.parse(o.toString(), DateTimeFormatter.ISO_DATE_TIME);
    }
}
