package com.lzy.demo.sharding;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.entity.Order;
import com.lzy.demo.mybatis.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.annotation.Resource;

/**
 * 如果数据库表没有创建,先执行shardsphere/sql/shardingsphere-dml.sql
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication
@SpringBootTest
@MapperScan("com.lzy.demo.mybatis.mapper")
@ActiveProfiles({"shardingsphere-sharding", "mybatis-plus"})
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
