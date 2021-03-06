/*
 * Created by lzy on 2019-07-28 20:03.
 */
package com.lzy.demo.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

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

}
