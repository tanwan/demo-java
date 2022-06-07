package com.lzy.demo.spring.boot.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * {@code @PropertySource}可以引用外部配置文件,如果引用yml,需要指定factory
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
@PropertySource(value = "classpath:properties.yml", factory = YamlPropertyLoaderFactory.class)
public class PropertySourceWithFactoryConfig {
}
