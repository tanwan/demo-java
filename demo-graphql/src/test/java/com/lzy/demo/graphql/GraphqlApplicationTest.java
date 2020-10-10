package com.lzy.demo.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The type Graphql test.
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = GraphqlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GraphqlApplicationTest {

    /**
     * Test graphql.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGraphql() throws Exception {
        Thread.sleep(10000000L);
    }
}
