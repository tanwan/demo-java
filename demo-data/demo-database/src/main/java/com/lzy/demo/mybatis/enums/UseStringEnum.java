/*
 * Created by lzy on 2019-07-27 14:28.
 */
package com.lzy.demo.mybatis.enums;

/**
 * @author lzy
 * @version v1.0
 */
public enum UseStringEnum {

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
