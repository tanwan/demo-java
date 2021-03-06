/*
 * Created by lzy on 9/4/17.
 */
package com.lzy.demo.jpa.entity;

import javax.persistence.*;

/**
 * 乐观锁机制
 *
 * @author lzy
 * @version v1.0
 */
@Entity
public class SimpleOptimisticLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Version
    private Integer version;

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
        return "SimpleOptimisticLock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
