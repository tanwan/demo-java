package com.lzy.demo.jdbi.entity;

import com.lzy.demo.enums.UseEnumValueEnum;
import com.lzy.demo.enums.UseStringEnum;

public class SimpleJdbi {

    private Integer id;


    private String name;

    private UseStringEnum useStringEnum;

    private UseEnumValueEnum useEnumValueEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UseStringEnum getUseStringEnum() {
        return useStringEnum;
    }

    public void setUseStringEnum(UseStringEnum useStringEnum) {
        this.useStringEnum = useStringEnum;
    }

    public UseEnumValueEnum getUseEnumValueEnum() {
        return useEnumValueEnum;
    }

    public void setUseEnumValueEnum(UseEnumValueEnum useEnumValueEnum) {
        this.useEnumValueEnum = useEnumValueEnum;
    }

    @Override
    public String toString() {
        return "SimpleJdbi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", useStringEnum=" + useStringEnum +
                ", useEnumValueEnum=" + useEnumValueEnum +
                '}';
    }

    private Integer generatedKey;

    public Integer getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(Integer generatedKey) {
        this.generatedKey = generatedKey;
    }
}
