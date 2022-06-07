package com.lzy.demo.spring.boot.properties;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * {@code @ConfigurationProperties}和{@code @Value}测试
 *
 * @author lzy
 * @version v1.0
 */
@EnableConfigurationProperties(AtConfigurationProperties.class)
@TestPropertySource(properties = "spring.config.location=classpath:properties.yml")
@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {CustomConverter.class, AtValue.class})
@Slf4j
public class PropertiesTest {

    /**
     * 测试@ConfigurationProperties
     *
     * @param atConfigurationProperties the at configuration properties
     */
    @Test
    public void testConfigurationProperties(@Autowired AtConfigurationProperties atConfigurationProperties) {
        System.out.println(atConfigurationProperties);
        assertThat(atConfigurationProperties)
                .hasFieldOrPropertyWithValue("integer", 1)
                .hasFieldOrPropertyWithValue("aDouble", 1.1)
                .hasFieldOrPropertyWithValue("str", "str")
                .hasFieldOrPropertyWithValue("localDateTime", LocalDateTime.of(2018, 11, 3, 10, 55, 22))
                .hasFieldOrPropertyWithValue("email", "99156629@qq.com")
                .hasFieldOrPropertyWithValue("actualValue", "expectValue")
                .hasFieldOrProperty("random")
                .hasFieldOrProperty("referenceExist")
                .hasFieldOrPropertyWithValue("referenceNotExist", "${not.exist.key}")
                .hasFieldOrPropertyWithValue("referenceDefaultValue", "defaultValue");
        assertThat(atConfigurationProperties.getRandom()).isNotEqualTo(atConfigurationProperties.getReferenceExist());
        assertThat(atConfigurationProperties.getMap1()).containsEntry("k1", "v1").containsEntry("k2", "v2").containsEntry("k3", "v3").containsEntry("/k4", "v4");
        assertThat(atConfigurationProperties.getMap2()).containsEntry("k1", "v1").containsEntry("k2", "v2").containsEntry("k3", "v3").containsEntry("/k4", "v4");
        assertThat(atConfigurationProperties.getList1()).contains("list1", "list2");
        assertThat(atConfigurationProperties.getList2()).contains("list1", "list2");
        assertThat(atConfigurationProperties.getArray()).contains("array1", "array2");
        assertThat(atConfigurationProperties.getAnotherClass())
                .hasFieldOrPropertyWithValue("property1", "property1")
                .hasFieldOrPropertyWithValue("property2", "property2");
        assertThat(atConfigurationProperties.getAnotherClasses())
                .extracting("property1", "property2")
                //property1和property2组成一个tuple
                .contains(tuple("property1", "property2"));
        assertThat(atConfigurationProperties.getCustomClass()).hasFieldOrPropertyWithValue("name", "customerClass");
    }

    /**
     * 测试@Value.
     *
     * @param atValue the at value
     */
    @Test
    public void testAtValue(@Autowired AtValue atValue) {
        System.out.println(atValue);
        assertThat(atValue)
                .hasFieldOrPropertyWithValue("integer", 1)
                .hasFieldOrPropertyWithValue("aDouble", 1.1)
                .hasFieldOrPropertyWithValue("str", "str")
                .hasFieldOrPropertyWithValue("list2", Arrays.asList("list1", "list2"))
                .hasFieldOrPropertyWithValue("spel", 6);
    }


}
