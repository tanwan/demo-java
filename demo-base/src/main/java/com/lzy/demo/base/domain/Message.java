/*
 * Created by LZY on 2016-09-24.
 */
package com.lzy.demo.base.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lzy.demo.base.constant.GlobalConstants;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息体格式
 *
 * @author LZY
 * @version v1.0
 */
@JsonInclude(Include.NON_NULL)
@Data
public class Message {
    public Message() {

    }

    public Message(Map<String, Object> results) {
        this.results = results;
    }

    /**
     * 返回的代码0为成功
     */
    private String code = "0";

    /**
     * 错误消息
     */
    private String error;

    private Map<String, Object> results;

    /**
     * 把结果转放到results中
     *
     * @param results the results
     */
    public void setResults(Object results) {
        if (results instanceof Map) {
            this.results = (Map) results;
        }
        this.results = new HashMap<>();
        this.results.put(GlobalConstants.RESULTS, results);
    }

    /**
     * 为响应消息添加错误信息
     *
     * @param code  the code
     * @param error the error
     */
    public void addErrorMessage(String code, String error) {
        this.code = code;
        this.error = error;
    }

    @Override
    public String toString() {
        return "Message{"
                + "code='" + code + '\''
                + ", error='" + error + '\''
                + ", results=" + results
                + '}';
    }
}
