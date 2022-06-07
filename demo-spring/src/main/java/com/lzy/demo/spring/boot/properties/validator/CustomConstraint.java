package com.lzy.demo.spring.boot.properties.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义校验
 *
 * @author lzy
 * @version v1.0  See <a href = "http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/"/>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomValidator.class})
public @interface CustomConstraint {

    /**
     * 期望的值
     *
     * @return string
     */
    String expectValue();

    /**
     * 错误消息,${validatedValue}表示当前校验的值,{expectValue}表示引用当前注解的属性的值(这个注解有expectValue)
     * {}也可以是国际化资源文件的值,国际化文件默认的基础名称是ValidationMessage,然后放在classpath根路径下,可以自定义路径
     *
     * @return the string
     */
    String message() default "${validatedValue} is not equals {expectValue}";

    /**
     * 用于指定这个约束条件属于哪(些)个校验组
     *
     * @return the class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * 可以通过此属性来给约束条件指定严重级别
     *
     * @return the class [ ]
     */
    Class<? extends Payload>[] payload() default {};
}
