/*
 * Created by lzy on 2020/5/26 2:42 PM.
 */
package com.lzy.demo.spring.mvc.bean;

import javax.validation.constraints.NotEmpty;

/**
 * @author lzy
 * @version v1.0
 */
public class ValidateMessage {

    /**
     * 不能为空
     */
    @NotEmpty(message = "不能为空")
    private String notEmpty;

    public String getNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(String notEmpty) {
        this.notEmpty = notEmpty;
    }

    @Override
    public String toString() {
        return "ValidateMessage{" +
                "notEmpty='" + notEmpty + '\'' +
                '}';
    }
}
