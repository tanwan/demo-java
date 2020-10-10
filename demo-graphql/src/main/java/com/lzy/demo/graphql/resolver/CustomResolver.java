package com.lzy.demo.graphql.resolver;

import com.lzy.demo.graphql.entity.Custom;
import com.lzy.demo.graphql.entity.SimpleEntity;
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
public class CustomResolver implements GraphQLResolver<Custom> {

    /**
     * Simple entity simple entity.
     *
     * @param custom com.lzy.demo.graphql.resolver.SimpleQueryResolver#customResolver()的返回值
     * @param env    the env
     * @return the simple entity
     */
    public SimpleEntity simpleEntity(Custom custom, DataFetchingEnvironment env) {
        return new SimpleEntity(1L, 23, "lzy");
    }

    /**
     * Simple entity with query type list.
     *
     * @param custom       com.lzy.demo.graphql.resolver.SimpleQueryResolver#customResolver()的返回值
     * @param simpleEntity the simple entity
     * @param env          the env
     * @return the list
     */
    public SimpleEntity simpleEntityWithQueryType(Custom custom, SimpleEntity simpleEntity, DataFetchingEnvironment env) {
        return new SimpleEntity(1L, 23, "lzy");
    }
}
