/*
 * Created by LZY on 2017/9/3 11:24.
 */
package com.lzy.demo.jpa.entity;

import javax.persistence.*;

/**
 * @author LZY
 * @version v1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Apple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
