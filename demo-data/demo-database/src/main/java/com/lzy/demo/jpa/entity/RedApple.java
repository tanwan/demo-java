/*
 * Created by LZY on 2017/9/3 11:25.
 */
package com.lzy.demo.jpa.entity;

import javax.persistence.Entity;

/**
 * @author LZY
 * @version v1.0
 */
@Entity
public class RedApple extends Apple {

    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
