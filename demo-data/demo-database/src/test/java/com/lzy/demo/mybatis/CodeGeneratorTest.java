package com.lzy.demo.mybatis;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

public class CodeGeneratorTest {

    private static final String DATABASE_URL = "localhost:3306/demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String MODULE_NAME = "sample";
    private static final String TABLE_NAMES = "new_table";

    /**
     * 代码生成器
     */
    @Test
    public void testCodeGenerator() {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:mysql://" + DATABASE_URL + "?useUnicode=true&useSSL=false&characterEncoding=utf8", USERNAME, PASSWORD)
                // postgresql的schemaName
                //.schema("demo")
                .build();

        String projectPath = System.getProperty("user.dir");

        // 全局配置
        GlobalConfig gc = new GlobalConfig.Builder()
                .outputDir(projectPath + "/build/generated-src/mybatis/main")
                .author("lzy")
                .build();

        // 包配置
        PackageConfig pc = new PackageConfig.Builder("com.lzy.demo.mybatis", MODULE_NAME)
                .build();


        // 注入配置
        InjectionConfig cfg = new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                }).build();


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                //使用默认的文件mybatis-plus-generator包下的templates/mapper.xml.ftl
                .xml("/templates/mapper.xml")
                //配置自定义输出模板
                //指定自定义模板路径,不要带上.ftl/.vm,会根据使用的模板引擎自动识别
                //这边使用的是类路径下的entity.java.ftl文件,参考(mybatis-plus-generator包下的templates/entity.java.ftl)
                //如果要使用默认的文件,则设置为/templates/entity.java
                .entity("/mybatis/templates/entity.java")
                .build();


        // 策略配置
        StrategyConfig strategy = new StrategyConfig.Builder()
                // 需要生成代码的表名
                .addInclude(TABLE_NAMES.split(","))
                .entityBuilder()
                // 表名转实体名策略
                .naming(NamingStrategy.underline_to_camel)
                // 字段名转属性策略
                .columnNaming(NamingStrategy.underline_to_camel)
                // 使用lombok
                .enableLombok()
                // 写于父类中的公共字段
                .addSuperEntityColumns("id")
                .build()
                .controllerBuilder()
                .enableRestStyle()
                // url用连字符
                .enableHyphenStyle()
                .build();


        // 代码生成器
        new AutoGenerator(dsc)
                .global(gc).packageInfo(pc)
                .injection(cfg)
                .template(templateConfig)
                .strategy(strategy)
                //默认是velocity
                .execute(new FreemarkerTemplateEngine());
    }
}
