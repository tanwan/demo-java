package com.lzy.demo.utils;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

public class ConfigUtils {

    private static final PropertySource<?> PROPERTY_SOURCE;

    static {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("application.yml");
        List<PropertySource<?>> propertySources;
        try {
            propertySources = new YamlPropertySourceLoader().load(resource.getFilename(), resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PROPERTY_SOURCE = propertySources.get(0);
    }

    public static String getConfig(String name) {
        return String.valueOf(PROPERTY_SOURCE.getProperty(name));
    }

    public static String getDBUrl() {
        return getConfig("spring.datasource.url");
    }

    public static String getDBUsername() {
        return getConfig("spring.datasource.username");
    }

    public static String getDBPassword() {
        return getConfig("spring.datasource.password");
    }
}
