package com.lzy.demo.graphql.controller;

import com.lzy.demo.graphql.entity.SimpleEntity;
import com.lzy.demo.graphql.entity.SimpleRequest;
import graphql.GraphQLContext;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

@Controller
public class GraphqlController {

    public static final LongAdder LONGADDER = new LongAdder();

    /**
     * {@code @QueryMapping}的typeName是Query
     * query xxx{baseQuery} 会使用此方法处理
     *
     * @param context context
     * @return List
     */
    @QueryMapping
    public List<SimpleEntity> baseQuery(GraphQLContext context) {
        return Arrays.asList(new SimpleEntity(1L, 23, "SpringbootGraphqlController#baseQuery"), new SimpleEntity(2L, 24, "SpringbootGraphqlController#baseQuery"));
    }

    /**
     * typeName为SimpleEntity
     * SimpleEntity{schemaMapping}会使用此方法处理, 比如: query xxx{baseQuery{schemaMapping}}, query xxx{arguments{schemaMapping}}
     * 当返回值是多个SimpleEntity, 会出现n+1的问题, 每个SimpleEntity都需要调用一次这个方法, 可以使用@BatchMapping可以解决
     * 同时这边是入参是可以拿到typeName指定类的实例的
     *
     * @return Integer
     * @see GraphqlController#batchMapping(List)
     */
    @SchemaMapping(typeName = "SimpleEntity")
    public Integer schemaMapping(SimpleEntity simpleEntity) {
        System.out.println("schemaMapping:" + simpleEntity);
        LONGADDER.increment();
        return LONGADDER.intValue();
    }

    @QueryMapping
    public SimpleEntity arguments(@Argument("integer") Integer integer, @Argument("str") String str, GraphQLContext context) {
        return new SimpleEntity(1L, integer, "SpringbootGraphqlController#arguments:" + str);
    }

    @QueryMapping
    public SimpleEntity argumentsWithType(@Argument("request") SimpleRequest request) {
        return new SimpleEntity(request.getId(), 23, "SpringbootGraphqlController#argumentsWithType:" + request.getStr(), request.getDateTime());
    }

    /**
     * {@code @MutationMapping}的typeName是Mutation
     *
     * @param request request
     * @return SimpleEntity
     */
    @MutationMapping
    public SimpleEntity simpleMutation(@Argument("request") SimpleRequest request) {
        return new SimpleEntity(request.getId(), 23, request.getStr());
    }

    /**
     * 解决n+1问题
     *
     * @param list 需要查询batchMapping的SimpleEntity列表
     * @return List<Integer> 返回值需要是List,或者Map
     */
    @BatchMapping(typeName = "SimpleEntity")
    public List<Integer> batchMapping(List<SimpleEntity> list) {
        // 一次性查询出list的需要的batchMapping
        LONGADDER.increment();
        // 返回值需要是List,或者Map
        return Arrays.asList(LONGADDER.intValue(), LONGADDER.intValue());
    }
}
