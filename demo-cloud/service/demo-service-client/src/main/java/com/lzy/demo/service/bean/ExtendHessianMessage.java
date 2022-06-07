package com.lzy.demo.service.bean;

public class ExtendHessianMessage extends HessianMessage {

    /**
     * 跟父类属性相同,hessian序列化时,这个字段会为null
     */
    private String string;


    @Override
    public String getString() {
        return string;
    }

    @Override
    public void setString(String string) {
        this.string = string;
    }
}
