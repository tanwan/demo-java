package com.lzy.demo.base.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lzy.demo.base.constant.GlobalConstants;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@Data
public class Message {
    public Message() {

    }

    public Message(Map<String, Object> results) {
        this.results = results;
    }

    private String code = "0";

    private String error;

    private Map<String, Object> results;

    public void setResults(Object results) {
        if (results instanceof Map) {
            this.results = (Map) results;
        }
        this.results = new HashMap<>();
        this.results.put(GlobalConstants.RESULTS, results);
    }

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
