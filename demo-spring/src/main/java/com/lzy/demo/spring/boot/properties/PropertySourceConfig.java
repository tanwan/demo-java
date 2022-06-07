package com.lzy.demo.spring.boot.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;


/**
 * {@code @PropertySource}可以引用外部配置文件,不使用factory的情况
 * 只能加载property文件
 * yml文件的话,只能使用configuration.properties.key: value
 * @see Properties#load0(java.util.Properties.LineReader)
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
@PropertySource(value = "classpath:property-source.yml")
public class PropertySourceConfig {
}
