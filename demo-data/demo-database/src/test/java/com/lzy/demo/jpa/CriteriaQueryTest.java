package com.lzy.demo.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzy.demo.jpa.dao.SimpleJpaDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("jpa")
public class CriteriaQueryTest {

    @Resource
    private SimpleJpaDao simpleJpaDao;

    @Resource
    private ObjectMapper objectMapper;


    /**
     * 使用specification.
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#findAll(Specification)
     */
    @Test
    public void testSpecification() {
        //同CriteriaQuery
        System.out.println(simpleJpaDao.findAll((root, query, cb) ->
                cb.and(cb.equal(root.get("name").as(String.class), "lzy"))));
    }

    /**
     * 测试CriteriaQuery
     */
    @Test
    public void testCriteriaQuery() {
        System.out.println(simpleJpaDao.criteriaQuery("lzy", 3));
    }

    /**
     * 测试返回自定义的类
     */
    @Test
    public void testReturnCustom() {
        System.out.println(simpleJpaDao.criteriaQueryReturnCustom("lzy"));
    }

    /**
     * 测试返回Object
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testReturnObject() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.criteriaQueryReturnObject("lzy")));
    }

    /**
     * 测试返回Tuple
     */
    @Test
    public void testReturnTuple() {
        //返回Tuple
        simpleJpaDao.criteriaQueryReturnTuple("lzy")
                .forEach(tuple -> {
                    System.out.println(tuple.get("name"));
                    System.out.println(tuple.get(1));
                });
    }

    /**
     * 测试join
     */
    @Test
    public void testQueryJoin() {
        simpleJpaDao.criteriaQueryJoin(1, "lzy")
                .forEach(tuple -> {
                    System.out.println(tuple.get(0));
                    System.out.println(tuple.get(1));
                });
    }
}
