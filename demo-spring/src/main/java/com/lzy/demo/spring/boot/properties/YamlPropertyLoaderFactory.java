package com.lzy.demo.spring.boot.properties;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * {@code @PropertySource}可以引用外部配置文件,如果引用yml,需要指定factory
 *
 * @author lzy
 * @version v1.0
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        CompositePropertySource sources = new CompositePropertySource(Optional.ofNullable(name).orElse(resource.getResource().getFilename()));
        List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        if (CollectionUtils.isEmpty(propertySources)) {
            return super.createPropertySource(name, resource);
        }
        propertySources.forEach(sources::addPropertySource);
        return sources;
    }
}
