package com.lzy.demo.graphql.entity;

import java.time.LocalDateTime;
import java.util.Optional;

public class SimpleEntity {

    private Long id;
    private Integer integer;
    private String str;
    private LocalDateTime dateTime;
    private String withArgument;
    private String withDirective;

    private Integer nPlusOneProblem;

    private Integer batchMapping;

    public SimpleEntity() {
        this.dateTime = LocalDateTime.now();
    }

    public SimpleEntity(Long id, Integer integer, String str) {
        this();
        this.id = id;
        this.integer = integer;
        this.str = str;
    }

    public SimpleEntity(Long id, Integer integer, String str, LocalDateTime dateTime) {
        this.id = id;
        this.integer = integer;
        this.str = str;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getWithArgument() {
        return withArgument;
    }

    public void setWithArgument(String withArgument) {
        this.withArgument = withArgument;
    }

    public String getWithDirective() {
        return Optional.ofNullable(withDirective).orElse("default directive value");
    }

    public void setWithDirective(String withDirective) {
        this.withDirective = withDirective;
    }

    public Integer getnPlusOneProblem() {
        return nPlusOneProblem;
    }

    public void setnPlusOneProblem(Integer nPlusOneProblem) {
        this.nPlusOneProblem = nPlusOneProblem;
    }

    public Integer getBatchMapping() {
        return batchMapping;
    }

    public void setBatchMapping(Integer batchMapping) {
        this.batchMapping = batchMapping;
    }
}
