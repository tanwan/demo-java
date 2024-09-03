package com.lzy.demo.graphql.entity;

import java.time.LocalDateTime;

public class SimpleRequest {

    private Long id;
    private LocalDateTime dateTime;
    private String str;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
