package com.lzy.demo.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SimpleJpaDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest(classes = JpaApplication.class)
@ActiveProfiles("jpa")
public class JpaQueryTest {

    @Resource
    private SimpleJpaDao simpleJpaDao;

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
        System.out.println(simpleJpaDao.findByName("lzy", sort));

        //使用Sort
        System.out.println(simpleJpaDao.sortQuery("lzy", Sort.by(Sort.Order.desc("createTime"))));
    }


    /**
     * 测试分页
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testPage() throws JsonProcessingException {
        //使用分页 0表示第一页
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.findByName("lzy", pageable)));
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.pageQuery("lzy", pageable)));
    }

    /**
     * 测试in
     */
    @Test
    public void testInQuery() {
        System.out.println(simpleJpaDao.inQuery(Arrays.asList(1, 2, 3)));
    }


    /**
     * 测试返回List<String>
     */
    @Test
    public void testReturnList() {
        System.out.println(simpleJpaDao.returnList("lzy"));
    }

    /**
     * 测试返回{@code List<Map<String,Object>>}
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testReturnListMap() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.returnListMap("lzy")));
    }

    /**
     * 测试返回{@code List<Object[]>}
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testReturnListObjects() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.returnListObjects()));
    }

    /**
     * 测试返回Object
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testReturnObject() throws JsonProcessingException {
        // 其实是数组
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.returnObject()));
    }

    /**
     * 测试返回Object数组
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testReturnObjects() throws JsonProcessingException {
        // 其实是二维数组
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.returnObjects()));
    }

    /**
     * 测试返回Map
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testReturnMap() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.returnMap()));
    }

    /**
     * 测试使用原生sql
     */
    @Test
    public void testNativeQuery() {
        System.out.println(simpleJpaDao.nativeQuery(1));
    }

    /**
     * 测试使用原生sql
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void testNativeQueryPage() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 2);
        //使用nativeQuery
        System.out.println(objectMapper.writeValueAsString(simpleJpaDao.nativeQueryPage("lzy", pageable)));
    }

    /**
     * 使用自定义查询
     */
    @Test
    public void testCustomQuery() {
        System.out.println(simpleJpaDao.customQuery("lzy"));
    }


    /**
     * 使用 异步
     */
    @Test
    public void testCompletableFuture() {
        simpleJpaDao.findByName("lzy").whenComplete((list, e) -> System.out.println(list));
    }
}
