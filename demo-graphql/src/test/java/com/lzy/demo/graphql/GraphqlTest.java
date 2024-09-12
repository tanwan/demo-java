package com.lzy.demo.graphql;


import com.lzy.demo.graphql.controller.GraphqlController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@AutoConfigureGraphQlTester
public class GraphqlTest {

    @Autowired
    private GraphQlTester graphQlTester;

    /**
     * 测试基础使用
     */
    @Test
    public void testBase() {
        GraphQlTester.Response response;
        // 直接发送请求
        response = graphQlTester.document("""
                query testBase{
                    baseQuery{
                        id integer str dateTime withDirective
                    }
                }
                """).execute();
        response.path("$.data.baseQuery[0].id").entity(Long.class).isEqualTo(1L);
        response.path("$.data.baseQuery[1].str").entity(String.class).isEqualTo("SpringbootGraphqlController#baseQuery");
        response.path("$.data.baseQuery[0].withDirective").entity(String.class).isEqualTo("DEFAULT DIRECTIVE VALUE");

        // 使用graphql-test的请求
        response = graphQlTester.documentName("base").execute();
        response.path("$.data.baseQuery[0].id").entity(Long.class).isEqualTo(1L);
        response.path("$.data.baseQuery[1].str").entity(String.class).isEqualTo("SpringbootGraphqlController#baseQuery");
        response.path("$.data.baseQuery[0].withDirective").entity(String.class).isEqualTo("DEFAULT DIRECTIVE VALUE");
    }

    /**
     * 测试使用请求参数
     */
    @Test
    public void testArgument() {
        GraphQlTester.Response response = graphQlTester.document("""
                        query testArguments($integer: Int!,$str: String!){
                            arguments(integer:$integer, str:$str){
                                id
                                integer
                                str
                                dateTime
                                schemaMapping
                            }
                        }
                        """).variable("integer", 23)
                .variable("str", "str")
                .execute();
        response.path("$.data.arguments.integer").entity(Integer.class).isEqualTo(23);
        response.path("$.data.arguments.str").entity(String.class).isEqualTo("SpringbootGraphqlController#arguments:str");

        // 使用graphql-test的请求
        response = graphQlTester.documentName("argument")
                .variable("integer", 23)
                .variable("str", "str").execute();
        response.path("$.data.arguments.integer").entity(Integer.class).isEqualTo(23);
        response.path("$.data.arguments.str").entity(String.class).isEqualTo("SpringbootGraphqlController#arguments:str");
    }


    /**
     * 测试使用定义参数的请求
     */
    @Test
    public void testArgumentsWithType() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 23);
        map.put("str", "str");
        map.put("dateTime", "2022-01-01 01:01:01");
        GraphQlTester.Response response = graphQlTester.documentName("argument-type")
                .variable("request", map)
                .execute();
        response.path("$.data.argumentsWithType.id").entity(Integer.class).isEqualTo(23);
        response.path("$.data.argumentsWithType.str").entity(String.class).isEqualTo("SpringbootGraphqlController#argumentsWithType:str");
    }

    /**
     * 测试n+1问题
     */
    @Test
    public void testNPlusOneProblem() {
        int start = GraphqlController.LONGADDER.intValue();
        GraphQlTester.Response response;
        // 直接发送请求
        response = graphQlTester.document("""
                query testBase{
                    baseQuery{
                        id schemaMapping
                    }
                }
                """).execute();
        response.path("$.data.baseQuery[0].schemaMapping").entity(Integer.class).isEqualTo(start + 1);
        response.path("$.data.baseQuery[1].schemaMapping").entity(Integer.class).isEqualTo(start + 2);

        // 使用batchMapping则不会有这个问题
        start = GraphqlController.LONGADDER.intValue();
        response = graphQlTester.document("""
                query testBase{
                    baseQuery{
                        id batchMapping
                    }
                }
                """).execute();
        response.path("$.data.baseQuery[0].batchMapping").entity(Integer.class).isEqualTo(start + 1);
        response.path("$.data.baseQuery[1].batchMapping").entity(Integer.class).isEqualTo(start + 1);
    }
}
