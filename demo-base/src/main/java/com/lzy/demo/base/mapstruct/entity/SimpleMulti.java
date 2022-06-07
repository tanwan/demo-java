package com.lzy.demo.base.mapstruct.entity;

import lombok.Data;

@Data
public class SimpleMulti {
    private String str;

    private Integer integer;

    private SimpleDestination destination;
}
