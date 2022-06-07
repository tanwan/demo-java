package com.lzy.demo.base.mapstruct.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleDestination {

    private String str;

    private Integer integer;

    private LocalDateTime localDateTime;

    private String stringLocalDateTime;

    private String differentDestination;

    private SimpleEnumDestination simpleEnum;

    private String beforeMapping;

    private String afterMapping;

    private String ignore;

    private Integer decuple;
}
