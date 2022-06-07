package com.lzy.demo.service.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * hessian必须实现Serializable
 *
 * @author lzy
 * @version v1.0
 */
public class HessianMessage implements Serializable {

    private String string;

    private BigDecimal bigDecimal;

    private Long numberLong;

    /**
     * hessian目前默认不支持localDateTime,如果要使用,则需要自己实现序列化
     */
    private LocalDateTime localDateTime;

    private HessianMessage innerMessage;

    private List<HessianMessage> innerMessageList;

    private Map<String, HessianMessage> innerMessageMap;


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Long getNumberLong() {
        return numberLong;
    }

    public void setNumberLong(Long numberLong) {
        this.numberLong = numberLong;
    }

    public HessianMessage getInnerMessage() {
        return innerMessage;
    }

    public void setInnerMessage(HessianMessage innerMessage) {
        this.innerMessage = innerMessage;
    }

    public List<HessianMessage> getInnerMessageList() {
        return innerMessageList;
    }

    public void setInnerMessageList(List<HessianMessage> innerMessageList) {
        this.innerMessageList = innerMessageList;
    }

    public Map<String, HessianMessage> getInnerMessageMap() {
        return innerMessageMap;
    }

    public void setInnerMessageMap(Map<String, HessianMessage> innerMessageMap) {
        this.innerMessageMap = innerMessageMap;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "HessianMessage{" +
                "string='" + string + '\'' +
                ", bigDecimal=" + bigDecimal +
                ", numberLong=" + numberLong +
                ", localDateTime=" + localDateTime +
                ", innerMessage=" + innerMessage +
                ", innerMessageList=" + innerMessageList +
                ", innerMessageMap=" + innerMessageMap +
                '}';
    }
}
