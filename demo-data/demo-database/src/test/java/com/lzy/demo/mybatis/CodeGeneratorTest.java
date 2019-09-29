/*
 * Created by lzy on 2019/9/27 3:54 PM.
 */
package com.lzy.demo.mybatis;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Code generator test.
 *
 * @author lzy
 * @version v1.0
 */
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
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // 配置输出路径
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("lzy");
        // 生成后,是否打开目录
        gc.setOpen(true);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + DATABASE_URL + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // postgresql的schemaName
        //dsc.setSchemaName("demo");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(MODULE_NAME);
        pc.setParent("com.lzy");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // freemarker:/templates/mapper.xml.ftl,velocity:/templates/mapper.xml.vm,
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名,如果Entity设置了前后缀,此处注意xml的名称会跟着发生变化
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 由于使用了自定义输出xml,所以就不需要输出xml,因此置为null
        templateConfig.setXml(null);

        //配置自定义输出模板
        //指定自定义模板路径,不要带上.ftl/.vm,会根据使用的模板引擎自动识别
        templateConfig.setEntity("/mybatis/template/entity.java");
        //templateConfig.setService();
        //templateConfig.setController();
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名转实体名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 字段名转属性策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 自定义的父类(全限定名)
        //strategy.setSuperEntityClass("");
        // 使用lombok
        strategy.setEntityLombokModel(true);
        // 使用restController
        strategy.setRestControllerStyle(true);
        // url用连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 设置公共父类(全限定名)
        //strategy.setSuperControllerClass("");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");

        // 表名
        strategy.setInclude(TABLE_NAMES.split(","));

        // 表前缀
        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
