package com.lzy.demo.graphql.resolver;

import com.lzy.demo.graphql.entity.CustomWithResolver;
import com.lzy.demo.graphql.entity.SimpleEntity;
import com.lzy.demo.graphql.entity.SimpleRequest;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

/**
 * Custom的GraphQLResolver
 * 因为Custom存在GraphQLResolver,所以会先调用该类匹配的方法,不存在时,才会去调用Custom的方法
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class CustomResolver implements GraphQLResolver<CustomWithResolver> {

    /**
     * 解析simpleEntity
     *
     * @param customWithResolver SimpleQueryResolver#customWithResolver的返回值
     * @param env                the env
     * @return the simple entity
     */
    public SimpleEntity simpleEntity(CustomWithResolver customWithResolver, DataFetchingEnvironment env) {
        return new SimpleEntity(1L, 23, "CustomResolver#simpleEntity:" + customWithResolver.getValue());
    }

    /**
     * 带有请求参数
     *
     * @param customWithResolver com.lzy.demo.graphql.resolver.SimpleQueryResolver#customResolver()的返回值
     * @param request            request
     * @param env                the env
     * @return the list
     */
    public SimpleEntity argumentsWithType(CustomWithResolver customWithResolver, SimpleRequest request, DataFetchingEnvironment env) {
        return new SimpleEntity(request.getId(), 23, "CustomResolver#argumentsWithType:" + customWithResolver.getValue());
    }
}
