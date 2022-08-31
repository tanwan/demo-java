package com.lzy.demo.graphql.controller;

import com.lzy.demo.graphql.entity.SimpleEntity;
import com.lzy.demo.graphql.entity.SimpleRequest;
import com.lzy.demo.graphql.entity.WithoutResolver;
import graphql.GraphQLContext;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class SpringbootGraphqlController {

    @QueryMapping
    public List<SimpleEntity> baseQuery(GraphQLContext context) {
        return Arrays.asList(new SimpleEntity(1L, 23, "SpringbootGraphqlController#baseQuery"), new SimpleEntity(2L, 24, "SpringbootGraphqlController#baseQuery"));
    }

    @QueryMapping
    public SimpleEntity arguments(@Argument Integer integer, @Argument String str, GraphQLContext context) {
        return new SimpleEntity(1L, integer, "SpringbootGraphqlController#arguments:" + str);
    }

    @QueryMapping
    public SimpleEntity argumentsWithType(@Argument SimpleRequest request) {
        return new SimpleEntity(request.getId(), 23, "SpringbootGraphqlController#argumentsWithType:" + request.getStr(), request.getDateTime(), request.getCommonDateTime());
    }

    @MutationMapping
    public SimpleEntity simpleMutation(@Argument SimpleRequest request) {
        return new SimpleEntity(request.getId(), 23, request.getStr());
    }

    @QueryMapping
    public WithoutResolver withoutResolver() {
        return new WithoutResolver();
    }
}
