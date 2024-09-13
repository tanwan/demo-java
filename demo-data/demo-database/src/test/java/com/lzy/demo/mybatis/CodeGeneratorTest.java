package com.lzy.demo.mybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.lzy.demo.utils.ConfigUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

public class CodeGeneratorTest {

    @Test
    public void testCodeGenerator() {
        List<String> tables = List.of("simple_mybatis");

        // 配置postgresql的schema需要使用com.baomidou.mybatisplus.generator.FastAutoGenerator.create(com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder)
        FastAutoGenerator.create(ConfigUtils.getDBUrl(), ConfigUtils.getDBUsername(), ConfigUtils.getDBPassword())
                .globalConfig(builder -> builder
                        .author("lzy")
                        // 保存的路径
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/generated/mybatis")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        // 指定包名
                        .parent("com.lzy.demo.mybatis")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper")
                )
                // 默认使用的模板见: com.baomidou.mybatisplus.generator.config.ConstVal, 都在mybatis-plus-generator下
                // 如果要使用自定义的模板, 可以通过各自的builder(entity,mapper,controller等)进行自定义
                .strategyConfig(builder -> builder
                        // 要生成代码的表名
                        .addInclude(tables)

                        .entityBuilder()
                        // 表名转实体名策略
                        .naming(NamingStrategy.underline_to_camel)
                        // 字段名转属性策略
                        .columnNaming(NamingStrategy.underline_to_camel)
                        // 使用lombok
                        .enableLombok()
                        // 写于父类中的公共字段
                        .addSuperEntityColumns("id")

                        .controllerBuilder()
                        .enableRestStyle()
                        // url用连字符
                        .enableHyphenStyle()

                )
                .injectionConfig(builder ->
                        builder.beforeOutputFile((tableInfo, objectMap) ->
                                System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size())))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
