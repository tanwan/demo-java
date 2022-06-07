package com.lzy.demo.graphql.resolver;

import com.lzy.demo.graphql.entity.SimpleEntity;
import com.lzy.demo.graphql.entity.SimpleRequest;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

/**
 * mutation需要实现GraphQLMutationResolver,可以单独一个类实现GraphQLMutationResolver,也可以一个类同时实现GraphQLQueryResolver和GraphQLMutationResolver
 * 用来增删改
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleMutationResolver implements GraphQLMutationResolver {


    /**
     * Simple mutation simple entity.
     *
     * @param request the request
     * @return the simple entity
     */
    public SimpleEntity simpleMutation(SimpleRequest request) {
        return new SimpleEntity(request.getId(), 23, request.getStr());
    }
}
