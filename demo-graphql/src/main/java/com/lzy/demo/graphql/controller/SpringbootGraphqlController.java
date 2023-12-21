package com.lzy.demo.graphql.controller;

import com.lzy.demo.graphql.entity.SimpleEntity;
import com.lzy.demo.graphql.entity.SimpleRequest;
import com.lzy.demo.graphql.entity.WithoutResolver;
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
public class SpringbootGraphqlController {

    public static final LongAdder LONGADDER = new LongAdder();

    /**
     * {@code @QueryMapping}的typeName是Query
     *
     * @param context context
     * @return List
     */
    @QueryMapping
    public List<SimpleEntity> baseQuery(GraphQLContext context) {
        return Arrays.asList(new SimpleEntity(1L, 23, "SpringbootGraphqlController#baseQuery"), new SimpleEntity(2L, 24, "SpringbootGraphqlController#baseQuery"));
    }

    @QueryMapping
    public SimpleEntity arguments(@Argument("integer") Integer integer, @Argument("str") String str, GraphQLContext context) {
        return new SimpleEntity(1L, integer, "SpringbootGraphqlController#arguments:" + str);
    }

    @QueryMapping
    public SimpleEntity argumentsWithType(@Argument("request") SimpleRequest request) {
        return new SimpleEntity(request.getId(), 23, "SpringbootGraphqlController#argumentsWithType:" + request.getStr(), request.getDateTime(), request.getCommonDateTime());
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

    @QueryMapping
    public WithoutResolver withoutResolver() {
        return new WithoutResolver();
    }


    /**
     * 注解的typeName=SimpleEntity
     * 所以graphql请求nPlusOneProblem这个字段的话, 会在这边进行解析
     * 这边会出现n+1的问题
     * 当请求baseQuery的时候,返回值是多个SimpleEntity, 则每个SimpleEntity都需要调用一次nPlusOneProblem
     * 使用@BatchMapping可以解决
     *
     * @return Integer
     */
    @SchemaMapping(typeName = "SimpleEntity")
    public Integer nPlusOneProblem() {
        LONGADDER.increment();
        return LONGADDER.intValue();
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
