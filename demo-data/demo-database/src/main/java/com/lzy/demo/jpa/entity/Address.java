package com.lzy.demo.jpa.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
}
