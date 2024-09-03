package com.lzy.demo.graphql.directive;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

/**
 * 自定义指定
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        GraphQLFieldDefinition field = env.getElement();
        GraphQLObjectType parentType = (GraphQLObjectType) env.getFieldsContainer();
        //获取指令
        GraphQLAppliedDirective graphQLDirective = env.getAppliedDirective("simpleDirective");
        //获取指令的参数值
        System.out.println("str=" + graphQLDirective.getArgument("str").getValue());
        //获取原始的DataFetcher
        DataFetcher<?> originalFetcher = env.getCodeRegistry().getDataFetcher(parentType, field);
        //创建新的DataFetcher
        DataFetcher<?> dataFetcher = DataFetcherFactories
                .wrapDataFetcher(originalFetcher, ((dataFetchingEnvironment, value) -> {
                    if (value instanceof String) {
                        return ((String) value).toUpperCase();
                    }
                    return value;
                }));

        // 使用新的DataFetcher
        env.getCodeRegistry().dataFetcher(parentType, field, dataFetcher);
        return field;
    }
}
