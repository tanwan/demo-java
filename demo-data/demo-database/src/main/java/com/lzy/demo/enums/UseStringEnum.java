package com.lzy.demo.enums;

/**
 * 使用枚举字符串,数据库存储的是 ONE,TWO,THREE
 *
 * @author lzy
 * @version v1.0
 */
public enum UseStringEnum {


    /**
     * One use string enum.
     */
    ONE(1),

    /**
     * Two use string enum.
     */
    TWO(2),

    /**
     * Three use string enum.
     */
    THREE(3);
    private int code;

    UseStringEnum(int code) {
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
