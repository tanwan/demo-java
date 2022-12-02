package com.lzy.demo.spring.boot.properties.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author lzy
 * @version v1.0
 * See <a href = "http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/"/>
 */
public class CustomValidator implements ConstraintValidator<CustomConstraint, String> {

    private String expectValue;

    @Override
    public void initialize(CustomConstraint customConstraint) {
        expectValue = customConstraint.expectValue();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (expectValue.equals(value)) {
            return true;
        }
        //禁止默认消息返回,默认信息就是CustomConstraint#message指定的信息
        //context.disableDefaultConstraintViolation();
        //自定义返回消息,此方法相当于是新加了一个返回消息,因此需要禁止默认消息的返回
        //国际化文件默认的基础名称是ValidationMessage,然后放在classpath根路径下,可以自定义路径
        context.buildConstraintViolationWithTemplate("{actualNotEqualsExpect}").addConstraintViolation();
        return false;
    }
}
