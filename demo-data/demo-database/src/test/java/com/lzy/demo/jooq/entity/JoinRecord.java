package com.lzy.demo.jooq.entity;

import com.lzy.demo.jooq.tables.pojos.SimpleJooq;

public class JoinRecord extends SimpleJooq {

    private String name2;

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    @Override
    public String toString() {
        return "JoinRecord{" +
                "name='" + getName() + '\'' +
                ", name2='" + name2 + '\'' +
                '}';
    }
}
