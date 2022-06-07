package com.lzy.demo.graphql.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class SimpleRequest {

    private Long id;
    private OffsetDateTime dateTime;
    private String str;
    private LocalDateTime commonDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public LocalDateTime getCommonDateTime() {
        return commonDateTime;
    }

    public void setCommonDateTime(LocalDateTime commonDateTime) {
        this.commonDateTime = commonDateTime;
    }
}
