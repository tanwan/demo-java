package com.lzy.demo.jpa.entity;

import jakarta.persistence.Entity;

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
