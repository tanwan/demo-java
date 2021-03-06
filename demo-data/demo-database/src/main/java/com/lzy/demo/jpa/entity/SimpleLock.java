/*
 * Created by lzy on 9/4/17.
 */
package com.lzy.demo.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 锁机制
 *
 * @author lzy
 * @version v1.0
 */
@Entity
public class SimpleLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

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

    @Override
    public String toString() {
        return "SimpleLock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
