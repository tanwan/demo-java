package com.lzy.demo.spring.mvc.bean;

import jakarta.validation.constraints.NotEmpty;

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
