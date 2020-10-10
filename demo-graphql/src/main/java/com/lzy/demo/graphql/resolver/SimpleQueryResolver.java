/*
 * Created by lzy on 2020/9/29 14:24.
 */
package com.lzy.demo.graphql.resolver;

import com.lzy.demo.graphql.entity.Custom;
import com.lzy.demo.graphql.entity.SimpleEntity;
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
    public List<SimpleEntity> simpleEntity(DataFetchingEnvironment env) {
        return Arrays.asList(new SimpleEntity(1L, 23, "lzy"), new SimpleEntity(2L, 24, "lzy2"));
    }

    /**
     * Simple entity with query type list.
     *
     * @param simpleEntity the simple entity
     * @param env          the env
     * @return the list
     */
    public List<SimpleEntity> simpleEntityWithQueryType(SimpleEntity simpleEntity, DataFetchingEnvironment env) {
        return Arrays.asList(new SimpleEntity(1L, 23, "lzy"), new SimpleEntity(2L, 24, "lzy2"));
    }


    /**
     * query{customResolver{simpleEntityWithQueryType(query:{id:1}){id}}}
     * Custom有自己的GraphQLResolver,所以会先执行GraphQLResolver匹配的方法(此时Custom的方法可以省略),不存在时,再执行Custom的方法
     *
     * @see CustomResolver
     * @return the custom
     */
    public Custom customResolver (){
        return new Custom();
    }

}