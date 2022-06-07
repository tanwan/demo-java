package com.lzy.demo.base.mapstruct.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SimpleSource {
    private String str;

    private Integer integer;

    private LocalDateTime localDateTime;

    private String differentSource;

    private SimpleEnumSource simpleEnum;

    private String ignore;

    private Integer decuple;

    @Builder
    public SimpleSource(String str, Integer integer, LocalDateTime localDateTime, String differentSource, SimpleEnumSource simpleEnum, String ignore, Integer decuple) {
        this.str = str;
        this.integer = integer;
        this.localDateTime = localDateTime;
        this.differentSource = differentSource;
        this.simpleEnum = simpleEnum;
        this.ignore = ignore;
        this.decuple = decuple;
    }
}
