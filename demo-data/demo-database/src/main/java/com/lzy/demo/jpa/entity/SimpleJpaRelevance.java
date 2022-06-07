package com.lzy.demo.jpa.entity;

import javax.persistence.*;

@Entity
public class SimpleJpaRelevance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double size;

    /**
     * simpleJpaId会转为下划线分隔
     */
    @ManyToOne
    @JoinColumn(name = "simpleJpaId", referencedColumnName = "id")
    private SimpleJpa simpleJpa;

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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

}
