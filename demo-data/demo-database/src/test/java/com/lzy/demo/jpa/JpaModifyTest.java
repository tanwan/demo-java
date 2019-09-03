/*
 * Created by lzy on 2019/8/29 3:52 PM.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SampleJpaDao;
import com.lzy.demo.jpa.entity.SampleJpa;
import com.lzy.demo.jpa.service.SampleTransactionService;
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
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
@Commit
public class JpaModifyTest {

    @Resource
    private SampleJpaDao sampleJpaDao;

    @Resource
    private SampleTransactionService sampleTransactionService;


    /**
     * 测试插入,jpa的插入不会过滤空值,如果需要过滤空值,可以选择继承SimpleJpaRepository,然后使用EnableJpaRepositories的repositoryBaseClass设置为Dao的基类
     */
    @Test
    public void testInsert() {
        SampleJpa sampleJpa = new SampleJpa();
        sampleJpa.setName("save");
        sampleJpaDao.save(sampleJpa);
    }

    /**
     * 测试批量插入 jpa的批量插入其实就是循环
     *
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#saveAll(Iterable)
     */
    @Test
    public void testBulkInsert() {
        SampleJpa sampleJpa1 = new SampleJpa();
        sampleJpa1.setName("save");
        SampleJpa sampleJpa2 = new SampleJpa();
        sampleJpa2.setName("save2");
        sampleJpaDao.saveAll(Arrays.asList(sampleJpa1, sampleJpa2));
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        // 先按id查询,然后再调用CrudRepository#delete(T entity)
        sampleJpaDao.deleteById(10);
        sampleJpaDao.findById(11).ifPresent(sampleJpaDao::delete);
    }

    /**
     * 测试更新,无事务
     */
    @Test
    public void testUpdateWithoutTransaction() {
        SampleJpa sampleJpa = sampleJpaDao.findById(1).get();
        sampleJpa.setName("3");
        sampleJpaDao.save(sampleJpa);
    }

    /**
     * 测试更新,有事务
     */
    @Test
    public void testUpdateWithTransaction() {
        sampleTransactionService.update("5");
    }

    /**
     * 使用modify
     */
    @Test
    public void testModify() {
        System.out.println("modify: " + sampleJpaDao.useModifying("lzy", 1));
    }
}
