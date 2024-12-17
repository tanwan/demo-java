package com.lzy.demo.spring.boot.properties;

import com.lzy.demo.spring.boot.properties.validator.CustomConstraint;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 使用@EnableConfigurationProperties来注册@ConfigurationProperties
 * 也可以通过@ConfigurationPropertiesScan来扫描
 *
 * @author lzy
 * @version v1.0
 */
@ConfigurationProperties("configuration.properties")
@Data
@Validated
public class AtConfigurationProperties {

    private Integer integer;

    private Double aDouble;

    private String str;

    private Map<String, Object> map1;

    /**
     * map2:  {k1: v1,k2: 12}
     */
    private Map<String, Object> map2;

    private List<String> list1;

    /**
     * list2: list1,list2
     */
    private List<String> list2;

    private String[] array;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    private AnotherClass anotherClass;

    private List<AnotherClass> anotherClasses;

    /**
     * 自定义类型,使用自定义解析器解析
     *
     * @see CustomConverter
     */
    private CustomClass customClass;

    /**
     * 数据校验不为空并且是邮箱
     */
    @Email
    @NotNull
    private String email;

    /**
     * 自定义注解
     *
     * @see com.lzy.demo.spring.boot.properties.validator.CustomValidator
     */
    @CustomConstraint(expectValue = "expectValue")
    private String actualValue;

    /**
     * 引用随机数
     */
    private String random;
    /**
     * 引用前面的配置过的属性(不是已经解析过的值)
     * 这个引用的是random,其实也是先替换成${random.uuid},然后解析的时候,会再重新获取一次随机值,因此referenceExist跟random不会相等
     */
    private String referenceExist;
    /**
     * 配置不存在
     */
    private String referenceNotExist;
    /**
     * 使用默认值
     */
    private String referenceDefaultValue;

    @Data
    private static class AnotherClass {
        private String property1;
        private String property2;
    }
}
