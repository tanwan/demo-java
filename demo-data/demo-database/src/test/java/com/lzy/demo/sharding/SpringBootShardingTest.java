/*
 * Created by lzy on 2020/5/27 5:23 PM.
 */
package com.lzy.demo.sharding;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.entity.Order;
import com.lzy.demo.mybatis.mapper.OrderMapper;
import com.lzy.demo.sharding.application.Application;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

/**
 * The type Spring boot sharding test.
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = Application.class)
@MapperScan("com.lzy.demo.mybatis.mapper")
@TestPropertySource(properties = "spring.config.location=classpath:sharding/springboot-sharding.yml")
public class SpringBootShardingTest {

    @Resource
    private OrderMapper orderMapper;


    /**
     * 测试查询
     */
    @Test
    public void testSelect() {
        Order order = new Order();
        order.setUserId(1L);
        order.setOrderId(3L);
        System.out.println(orderMapper.selectOrder(order));
    }

    /**
     * 查询分页
     */
    @Test
    public void testPage() {
        Page<Order> page = new Page<>(0, 10);
        Order order = new Order();
        order.setUserId(1L);
        System.out.println(orderMapper.customPage(page, order));
    }
}
