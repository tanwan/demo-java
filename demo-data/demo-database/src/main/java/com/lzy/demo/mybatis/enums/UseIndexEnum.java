/*
 * Created by lzy on 2019-07-27 13:55.
 */
package com.lzy.demo.mybatis.enums;

/**
 * 使用枚举索引,数据库存储的是0,1,2,0表示ONE,1表示TWO,2表示THREE
 *
 * @author lzy
 * @version v1.0
 */
public enum UseIndexEnum {

    /**
     * One sample enum.
     */
    ONE(1),
    /**
     * Two sample enum.
     */
    TWO(2),
    /**
     * Three sample enum.
     */
    THREE(3);
    private int code;

    UseIndexEnum(int code) {
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
