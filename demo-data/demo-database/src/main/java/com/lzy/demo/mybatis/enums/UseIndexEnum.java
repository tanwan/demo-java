/*
 * Created by lzy on 2019-07-27 13:55.
 */
package com.lzy.demo.mybatis.enums;

/**
 * The enum Sample enum.
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
