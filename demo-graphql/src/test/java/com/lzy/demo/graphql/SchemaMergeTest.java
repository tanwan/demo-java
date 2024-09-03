package com.lzy.demo.graphql;

import com.lzy.demo.graphql.scalar.CustomScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.SchemaPrinter;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SchemaMergeTest {

    /**
     * 可以参考此代码, 在buildSrc(gradle可以直接调用)模块下开发一个类给gradle调用
     * @throws Exception exception
     */
    @Test
    public void testMerge() throws Exception {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        SchemaPrinter schemaPrinter = new SchemaPrinter();

        Path schemeDir = Paths.get(SchemaMergeTest.class.getClassLoader().getResource("graphql/schema.graphqls").toURI()).getParent();

        Files.walk(schemeDir)
                .filter(p -> p.toString().endsWith(".graphqls"))
                .forEach(p -> typeRegistry.merge(schemaParser.parse(p.toFile())));


        RuntimeWiring runtimeWiring = RuntimeWiring
                .newRuntimeWiring()
                // 自定义scalar
                .scalar(CustomScalars.DATE_TIME)
                .build();

        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

        String schema = schemaPrinter.print(graphQLSchema);
        Files.write(Paths.get(schemeDir.getParent().toString(), "schema.graphqls"), schema.getBytes(StandardCharsets.UTF_8));
        System.out.println(schema);
    }
}
