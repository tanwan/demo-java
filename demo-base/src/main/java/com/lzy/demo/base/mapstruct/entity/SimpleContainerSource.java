package com.lzy.demo.base.mapstruct.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class SimpleContainerSource {

    private SimpleSource simpleSource;

    private List<SimpleSource> list;

    private Set<SimpleSource> set;

    private Map<String, SimpleSource> map;
}
