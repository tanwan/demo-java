package com.lzy.demo.graphql;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;


@GraphQLTest
@ComponentScan(basePackageClasses = GraphqlApplication.class)
public class GraphqlTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    /**
     * 测试基础使用
     */
    @Test
    public void testBase() {
        // testBase是operationName,可以省略
        String graphql = "{\"query\":\"query testBase{baseQuery{id integer str dateTime commonDateTime}}\"}";
        GraphQLResponse response = graphQLTestTemplate.post(graphql);
        Assertions.assertThat(response.get("$.data.baseQuery[0].id")).isEqualTo("1");
        Assertions.assertThat(response.get("$.data.baseQuery[1].str")).isEqualTo("SimpleQueryResolver#baseQuery");
        System.out.println(response.getRawResponse().getBody());
    }

    /**
     * 测试使用请求参数
     * @throws IOException exception
     */
    @Test
    public void testArguments() throws IOException {
        // 在这边需要使用str:\\\"str\\\", 实际请求需要需要str:"str"
        String graphql = "{\"query\":\"query testArguments{arguments(integer:23, str:\\\"str\\\"){id integer str}}\"}";
        GraphQLResponse response = graphQLTestTemplate.post(graphql);
        Assertions.assertThat(response.get("$.data.arguments.str")).isEqualTo("SimpleQueryResolver#arguments:str");

        // 请求使用了参数, operationName同样也可以省略
        graphql = "{\"query\":\"query ($integer: Int!,$str: String!){arguments(integer:$integer, str:$str){id integer str}}\",\"variables\":{\"integer\":23,\"str\":\"str\"}}";
        response = graphQLTestTemplate.post(graphql);
        System.out.println(response.getRawResponse().getBody());

        // 使用了文件
        // 直接使用JsonNodeFactory,也可以通ObjectMapper
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("integer", 3);
        objectNode.put("str", "str");
        response = graphQLTestTemplate.perform("graphql/argument-test.graphql", objectNode);
        System.out.println(response.getRawResponse().getBody());
    }

    /**
     * 测试使用定义参数的请求
     * @throws IOException exception
     */
    @Test
    public void testArgumentsWithType() throws IOException {
        // 在这边需要使用str:\\\"str\\\", 实际请求需要需要str:"str"
        String graphql = "{\"query\":\"query testArgumentsWithType{argumentsWithType(request:{id:1, str:\\\"str\\\",dateTime:\\\"2022-01-01T01:01:01.000000+08:00\\\"," +
                " commonDateTime:\\\"2022-01-01 01:01:01\\\"}){id integer str}}\"}";
        GraphQLResponse response = graphQLTestTemplate.post(graphql);
        Assertions.assertThat(response.get("$.data.argumentsWithType.str")).isEqualTo("SimpleQueryResolver#argumentsWithType:str");

        // 请求使用了参数
        graphql = "{\"query\":\"query testArgumentsWithType($id: ID!,$str: String!){argumentsWithType(request:{id:$id, str:$str}){id integer str}}\",\"variables\":{\"id\":1,\"str\":\"str\"}}";
        response = graphQLTestTemplate.post(graphql);
        System.out.println(response.getRawResponse().getBody());

        // 请求使用了参数
        graphql = "{\"query\":\"query testArgumentsWithType($request: SimpleRequest){argumentsWithType(request:$request){id integer str dateTime commonDateTime}}\"," +
                "\"variables\":{\"request\":{\"id\":1,\"str\":\"str\",\"dateTime\":\"2022-01-01T01:01:01.000000+08:00\", \"commonDateTime\":\"2022-01-01 01:01:01\"}}}";
        response = graphQLTestTemplate.post(graphql);
        System.out.println(response.getRawResponse().getBody());


        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        ObjectNode innerNode = JsonNodeFactory.instance.objectNode();
        innerNode.put("id", 1);
        innerNode.put("str", "str");
        innerNode.put("dateTime", "2022-01-01T01:01:01.000000+08:00");
        innerNode.put("commonDateTime", "2022-01-01 01:01:01");
        objectNode.set("request", innerNode);
        response = graphQLTestTemplate.perform("graphql/argument-type-test.graphql", objectNode);
        System.out.println(response.getRawResponse().getBody());
    }


    /**
     * 测试自定义Resolver
     */
    @Test
    public void testCustomResolver() {
        String graphql = "{\"query\":\"query testCustomResolver{customWithResolver{simpleEntity{id str}}}\"}";
        GraphQLResponse response = graphQLTestTemplate.post(graphql);
        Assertions.assertThat(response.get("$.data.customWithResolver.simpleEntity.str")).isEqualTo("CustomResolver#simpleEntity:customWithResolver");

        // 带请求参数
        graphql = "{\"query\":\"query{customWithResolver{argumentsWithType(request:{id:1}){id str}}}\"}";
        response = graphQLTestTemplate.post(graphql);
        System.out.println(response.getRawResponse().getBody());
    }

    /**
     * 测试没有定义Resolver
     */
    @Test
    public void testWithoutResolver() {
        String graphql = "{\"query\":\"query testWithoutResolver{withoutResolver{simpleEntity{id str}}}\"}";
        GraphQLResponse response = graphQLTestTemplate.post(graphql);
        Assertions.assertThat(response.get("$.data.withoutResolver.simpleEntity.str")).isEqualTo("WithoutResolver#simpleEntity");

        // 带请求参数
        graphql = "{\"query\":\"query{withoutResolver{argumentsWithType(request:{id:1}){id str}}}\"}";
        response = graphQLTestTemplate.post(graphql);
        System.out.println(response.getRawResponse().getBody());
    }

    /**
     * 测试指令
     *
     * @throws IOException the io exception
     */
    @Test
    public void testDirectives() throws IOException {
        // 直接使用graphqls文件
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/directive-test.graphql");
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.isOk()).isTrue();
        Assertions.assertThat(response.get("$.data.customWithResolver.argumentsWithType.withDirective")).isEqualTo("WITHDIRECTIVE");
    }
}
