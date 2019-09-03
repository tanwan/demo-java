/*
 * Created by LZY on 2017/9/4 22:07.
 */
package com.lzy.demo.jpa.entity;

import javax.persistence.*;

/**
 * @author LZY
 * @version v1.0
 */
@Entity
public class SampleJpaRelevance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double size;

    /**
     * sampleJpaId会转为下划线分隔
     */
    @ManyToOne
    @JoinColumn(name = "sampleJpaId", referencedColumnName = "id")
    private SampleJpa sampleJpa;

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
