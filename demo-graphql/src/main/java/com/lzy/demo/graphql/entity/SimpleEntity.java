package com.lzy.demo.graphql.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

public class SimpleEntity {

    private Long id;
    private Integer integer;
    private String str;
    private OffsetDateTime dateTime;
    private LocalDateTime commonDateTime;
    private String withArgument;
    private String withDirective;

    public SimpleEntity() {
        this.dateTime = OffsetDateTime.now();
        this.commonDateTime = LocalDateTime.now();
    }

    public SimpleEntity(Long id, Integer integer, String str) {
        this();
        this.id = id;
        this.integer = integer;
        this.str = str;
    }

    public SimpleEntity(Long id, Integer integer, String str, OffsetDateTime dateTime, LocalDateTime commonDateTime) {
        this.id = id;
        this.integer = integer;
        this.str = str;
        this.dateTime = dateTime;
        this.commonDateTime = commonDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getCommonDateTime() {
        return commonDateTime;
    }

    public void setCommonDateTime(LocalDateTime commonDateTime) {
        this.commonDateTime = commonDateTime;
    }

    public String getWithArgument() {
        return withArgument;
    }

    public void setWithArgument(String withArgument) {
        this.withArgument = withArgument;
    }

    public String getWithDirective() {
        return Optional.ofNullable(withDirective).orElse("withDirective");
    }

    public void setWithDirective(String withDirective) {
        this.withDirective = withDirective;
    }
}
