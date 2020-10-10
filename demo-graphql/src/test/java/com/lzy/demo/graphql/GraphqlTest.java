package com.lzy.demo.graphql;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


import java.io.IOException;


/**
 * The type Graphql test.
 *
 * @author lzy
 * @version v1.0
 */
@GraphQLTest
@ComponentScan(basePackageClasses = GraphqlApplication.class)
public class GraphqlTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    /**
     * 设置指令
     *
     * @throws IOException the io exception
     */
    @Test
    public void testDirectives() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/graphql-test.graphqls");
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.isOk()).isTrue();
        Assertions.assertThat(response.get("$.data.customResolver.simpleEntityWithQueryType.withDirective")).isEqualTo("WITHDIRECTIVE");
    }
}
