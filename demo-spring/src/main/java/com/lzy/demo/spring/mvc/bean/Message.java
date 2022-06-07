package com.lzy.demo.spring.mvc.bean;

/**
 * 消息体
 *
 * @author lzy
 * @version v1.0
 */
public class Message {
    private static final String SUCCESS_CODE = "0";

    /**
     * 数据
     */
    private Object data;

    /**
     * 错误编码,0表示成功
     */
    private String code = SUCCESS_CODE;

    public Message(Object data) {
        this.data = data;
    }

    public Message() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
