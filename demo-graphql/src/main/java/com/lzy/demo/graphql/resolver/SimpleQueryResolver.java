package com.lzy.demo.graphql.resolver;

import com.lzy.demo.graphql.entity.CustomWithResolver;
import com.lzy.demo.graphql.entity.SimpleEntity;
import com.lzy.demo.graphql.entity.SimpleRequest;
import com.lzy.demo.graphql.entity.WithoutResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 只需要实现GraphQLQueryResolver,就可以将方法同graphqls文件的Query,Mutation进行映射
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleQueryResolver implements GraphQLQueryResolver {

    /**
     * 方法跟graphqls文件的Query,Mutation的映射关系,只能一一对应,如果存在一对多,则会报错,可以有以下4种映射关系:
     * 1.method operation(*args)
     * 2.method isOperation(*args) 仅支持 return boolean
     * 3.method getOperation(*args)
     * 4.method getFieldOperation(*args)
     *
     * @param env the env
     * @return the list
     */
    public List<SimpleEntity> baseQuery(DataFetchingEnvironment env) {
        return Arrays.asList(new SimpleEntity(1L, 23, "SimpleQueryResolver#baseQuery"), new SimpleEntity(2L, 24, "SimpleQueryResolver#baseQuery"));
    }


    /**
     * 带有请求参数
     *
     * @param integer integer
     * @param str     string
     * @param env     the env
     * @return SimpleEntity
     */
    public SimpleEntity arguments(Integer integer, String str, DataFetchingEnvironment env) {
        // 使用env也可以获取到请求参数
        System.out.println("env.getArgument:" + env.getArgument("integer"));
        System.out.println("env.getArgument:" + env.getArgument("str"));
        System.out.println("env.getArguments:" + env.getArguments().entrySet());
        return new SimpleEntity(1L, integer, "SimpleQueryResolver#arguments:" + str);
    }

    /**
     * 带有请求参数, 使用定义的类型
     *
     * @param request request
     * @param env     the env
     * @return the list
     */
    public SimpleEntity argumentsWithType(SimpleRequest request, DataFetchingEnvironment env) {
        System.out.println("env.getArgument:" + env.getArgument("request"));
        System.out.println("env.getArguments:" + env.getArguments().entrySet());
        return new SimpleEntity(request.getId(), 23, "SimpleQueryResolver#argumentsWithType:" + request.getStr(), request.getDateTime(), request.getCommonDateTime());
    }


    /**
     * CustomWithResolver有自己的GraphQLResolver,所以会执行GraphQLResolver匹配的方法
     * query{customWithResolver{argumentsWithType(request:{id:1}){id str}}} => CustomResolver#argumentsWithType
     * query{customWithResolver{simpleEntity{id str}}} => CustomResolver#simpleEntity
     *
     * @return the CustomWithResolver
     * @see CustomResolver
     */
    public CustomWithResolver customWithResolver() {
        // 这边的创建的CustomWithResolver实例需要作为CustomResolver方法的入参
        return new CustomWithResolver("customWithResolver");
    }


    /**
     * WithoutResolver没有自己的GraphQLResolver,所以会执行WithoutResolver的方法
     * query{withoutResolver{argumentsWithType(request:{id:1}){id str}}} => WithoutResolver#argumentsWithType
     * query{withoutResolver{simpleEntity{id str}}} => WithoutResolver#simpleEntity
     *
     * @return the WithoutResolver
     * @see WithoutResolver
     */
    public WithoutResolver withoutResolver() {
        return new WithoutResolver();
    }
}
