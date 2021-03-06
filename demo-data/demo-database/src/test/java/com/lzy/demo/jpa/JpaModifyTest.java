/*
 * Created by lzy on 2019/8/29 3:52 PM.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SimpleJpaDaoSimple;
import com.lzy.demo.jpa.entity.SimpleJpa;
import com.lzy.demo.jpa.service.SimpleTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 插入,更新,删除测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.additional-location=classpath:jpa/jpa-hakari.yml")
@Commit
public class JpaModifyTest {

    @Resource
    private SimpleJpaDaoSimple simpleJpaDao;

    @Resource
    private SimpleTransactionService simpleTransactionService;


    /**
     * 测试插入,jpa的插入不会过滤空值,如果需要过滤空值,可以选择继承SimpleJpaRepository,然后使用EnableJpaRepositories的repositoryBaseClass设置为Dao的基类
     */
    @Test
    public void testInsert() {
        SimpleJpa simpleJpa = new SimpleJpa();
        simpleJpa.setName("save");
        simpleJpaDao.save(simpleJpa);
    }

    /**
     * 测试批量插入 jpa的批量插入其实就是循环
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#saveAll(Iterable)
     */
    @Test
    public void testBulkInsert() {
        SimpleJpa simpleJpa1 = new SimpleJpa();
        simpleJpa1.setName("save");
        SimpleJpa simpleJpa2 = new SimpleJpa();
        simpleJpa2.setName("save2");
        simpleJpaDao.saveAll(Arrays.asList(simpleJpa1, simpleJpa2));
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        // 先按id查询,然后再调用CrudRepository#delete(T entity)
        simpleJpaDao.deleteById(10);
        simpleJpaDao.findById(11).ifPresent(simpleJpaDao::delete);
    }

    /**
     * 测试更新,无事务
     */
    @Test
    public void testUpdateWithoutTransaction() {
        SimpleJpa simpleJpa = simpleJpaDao.findById(1).get();
        simpleJpa.setName("3");
        simpleJpaDao.save(simpleJpa);
    }

    /**
     * 测试更新,有事务
     */
    @Test
    public void testUpdateWithTransaction() {
        simpleTransactionService.update("5");
    }

    /**
     * 使用modify
     */
    @Test
    public void testModify() {
        System.out.println("modify: " + simpleJpaDao.useModifying("lzy", 1));
    }
}
