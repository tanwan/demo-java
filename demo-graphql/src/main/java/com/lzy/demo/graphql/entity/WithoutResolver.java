package com.lzy.demo.graphql.entity;

import graphql.schema.DataFetchingEnvironment;

/**
 * 没有GraphQLQueryResolver来解析此类,所以会执行此类的方法
 *
 * @author lzy
 * @version v1.0
 */
public class WithoutResolver {

    public SimpleEntity simpleEntity(DataFetchingEnvironment env) {
        return new SimpleEntity(1L, 23, "WithoutResolver#simpleEntity");
    }


    /**
     * 带有请求参数
     *
     * @param request request
     * @param env     env
     * @return simple entity
     */
    public SimpleEntity argumentsWithType(SimpleRequest request, DataFetchingEnvironment env) {
        return new SimpleEntity(request.getId(), 23, "WithoutResolver#argumentsWithType");
    }
}
