package com.lzy.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;

/**
 * 使用枚举值,数据库存储的是枚举的code,1表示ONE,2表示TWO,3表示THREE
 *
 * @author lzy
 * @version v1.0
 */
public enum UseEnumValueEnum {


    /**
     * One use enum value enum.
     */
    ONE(1),

    /**
     * Two use enum value enum.
     */
    TWO(2),

    /**
     * Three use enum value enum.
     */
    THREE(3);

    /**
     * mybatis-plus默认使用枚举值,使用 @EnumValue直接使用code存入数据库
     */
    @EnumValue
    private int code;

    UseEnumValueEnum(int code) {
        this.code = code;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    public static UseEnumValueEnum byCode(Integer code) {
        return Arrays.stream(UseEnumValueEnum.values()).filter(c -> c.getCode() == code).findFirst().orElse(null);
    }

}
