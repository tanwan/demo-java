package com.lzy.demo.jpa;

import com.lzy.demo.jpa.dao.SimpleJpaDao;
import com.lzy.demo.jpa.entity.SimpleJpa;
import com.lzy.demo.jpa.service.SimpleTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("jpa")
@Commit
public class JpaModifyTest {

    @Resource
    private SimpleJpaDao simpleJpaDao;

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
