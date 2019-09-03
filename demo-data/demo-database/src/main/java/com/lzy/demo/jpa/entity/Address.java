/*
 * Created by LZY on 2017/9/2 20:11.
 */
package com.lzy.demo.jpa.entity;

import javax.persistence.Embeddable;

/**
 * 地址
 *
 * @author LZY
 * @version v1.0
 */
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
