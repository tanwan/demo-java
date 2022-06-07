package com.lzy.demo.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询
     * 分页的参数需要放在第一个参数
     *
     * @param order the order
     * @param page  page
     * @return page page
     */
    Page<Order> customPage(Page<Order> page, @Param("order") Order order);


    /**
     * 查询列表
     *
     * @param order the order
     * @return the list
     */
    List<Order> selectOrder(Order order);
}
