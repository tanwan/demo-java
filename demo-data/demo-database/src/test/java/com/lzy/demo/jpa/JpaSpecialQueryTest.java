/*
 * Created by lzy on 2019-08-21 18:37.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SimpleJpaDaoSimple;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

/**
 * jpa特殊字查询
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
public class JpaSpecialQueryTest {

    @Resource
    private SimpleJpaDaoSimple simpleJpaDao;

    /**
     * 测试IgnoreCase
     */
    @Test
    public void testIgnoreCase() {
        System.out.println(simpleJpaDao.findByNameIgnoreCase("LZY"));
    }

    /**
     * 测试OrderBy
     */
    @Test
    public void testOrderBy() {
        System.out.println(simpleJpaDao.findByNameOrderByAgeDesc("lzy"));
    }

    /**
     * 测试Top
     */
    @Test
    public void testTop() {
        //使用Top
        System.out.println(simpleJpaDao.findTopByOrderByAgeDesc());
        //使用Top加数字
        System.out.println(simpleJpaDao.findTop2ByName("lzy", Sort.by("id")));
    }
}
