package com.lzy.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 使用关键字做表名需要添加反引号
 *
 * @author lzy
 * @version v1.0
 */
@TableName(value = "`order`")
public class Order {

    private Long id;

    private Long orderId;

    private Long userId;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
