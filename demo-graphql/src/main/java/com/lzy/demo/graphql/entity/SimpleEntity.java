package com.lzy.demo.graphql.entity;

import java.util.Optional;

/**
 * @author lzy
 * @version v1.0
 */
public class SimpleEntity {

    private Long id;
    private Integer age;
    private String name;
    private String withArgument;
    private String withDirective;

    public SimpleEntity() {
    }

    public SimpleEntity(Long id, Integer age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getWithArgument(Integer argu) {
        return argu + "";
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
