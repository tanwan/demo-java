package com.lzy.demo.spring.boot.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * 自定义ConfigurationProperties转化类
 *
 * @author lzy
 * @version v1.0
 */
@Component
@ConfigurationPropertiesBinding
public class CustomConverter implements Converter<String, CustomClass> {
    @Override
    public CustomClass convert(String source) {
        return Optional.ofNullable(source).map(CustomClass::new).orElse(null);
    }
}
