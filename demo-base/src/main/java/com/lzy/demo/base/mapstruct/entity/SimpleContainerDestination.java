package com.lzy.demo.base.mapstruct.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class SimpleContainerDestination {


    private SimpleDestination simpleDestination;

    private List<SimpleDestination> list;

    private Set<SimpleDestination> set;

    private Map<String, SimpleDestination> map;

}
