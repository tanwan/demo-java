/*
 * Created by LZY on 2017/3/11 16:02.
 */
package com.lzy.demo.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SampleJpaDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 查询测试
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
public class JpaQueryTest {

    @Resource
    private SampleJpaDao sampleJpaDao;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 测试sort
     */
    @Test
    public void testSort() {
        //Sort里的属性是实体类的属性名,而不是表的属性名
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "age");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = Sort.by(Arrays.asList(order1, order2));
        //使用Sort
        System.out.println(sampleJpaDao.findByName("lzy", sort));

        //使用Sort
        System.out.println(sampleJpaDao.sortQuery("lzy", Sort.by(Sort.Order.desc("createTime"))));
    }


    /**
     * 测试分页
     */
    @Test
    public void testPage() throws JsonProcessingException {
        //使用分页 0表示第一页
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.findByName("lzy", pageable)));
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.pageQuery("lzy", pageable)));
    }

    /**
     * 测试in
     */
    @Test
    public void testInQuery() {
        System.out.println(sampleJpaDao.inQuery(Arrays.asList(1, 2, 3)));
    }


    /**
     * 测试返回List<String>
     */
    @Test
    public void testReturnList() {
        System.out.println(sampleJpaDao.returnList("lzy"));
    }

    /**
     * 测试返回{@code List<Map<String,Object>>}
     */
    @Test
    public void testReturnListMap() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.returnListMap("lzy")));
    }

    /**
     * 测试返回{@code List<Object[]>}
     */
    @Test
    public void testReturnListObjects() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.returnListObjects()));
    }

    /**
     * 测试返回Object
     */
    @Test
    public void testReturnObject() throws JsonProcessingException {
        // 其实是数组
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.returnObject()));
    }

    /**
     * 测试返回Object数组
     */
    @Test
    public void testReturnObjects() throws JsonProcessingException {
        // 其实是二维数组
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.returnObjects()));
    }

    /**
     * 测试返回Map
     */
    @Test
    public void testReturnMap() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.returnMap()));
    }

    /**
     * 测试使用原生sql
     */
    @Test
    public void testNativeQuery() {
        System.out.println(sampleJpaDao.nativeQuery(1));
    }

    /**
     * 测试使用原生sql
     */
    @Test
    public void testNativeQueryPage() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 2);
        //使用nativeQuery
        System.out.println(objectMapper.writeValueAsString(sampleJpaDao.nativeQueryPage("lzy", pageable)));
    }

    /**
     * 使用自定义查询
     */
    @Test
    public void testCustomQuery() {
        System.out.println(sampleJpaDao.customQuery("lzy"));
    }


    /**
     * 使用 异步
     */
    @Test
    public void testCompletableFuture() {
        sampleJpaDao.findByName("lzy").whenComplete((list, e) -> System.out.println(list));
    }
}
