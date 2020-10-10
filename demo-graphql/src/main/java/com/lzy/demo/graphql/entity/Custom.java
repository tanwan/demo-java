package com.lzy.demo.graphql.entity;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author lzy
 * @version v1.0
 */
public class Custom {

    public SimpleEntity simpleEntity(DataFetchingEnvironment env) {
        return new SimpleEntity(1L, 23, "lzy");
    }
}
