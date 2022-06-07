package com.lzy.demo.jpa;

import com.lzy.demo.jpa.dao.SimpleJpaDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("jpa")
public class JpaSpecialQueryTest {

    @Resource
    private SimpleJpaDao simpleJpaDao;

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
