/*
 * Created by lzy on 2019/8/30 2:13 PM.
 */
package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.dao.SampleJpaDao;
import com.lzy.demo.jpa.entity.SampleJpa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * The type Abstract transaction service.
 *
 * @author lzy
 * @version v1.0
 */
public abstract class AbstractTransactionService {

    /**
     * The Sample jpa dao.
     */
    @Resource
    protected SampleJpaDao sampleJpaDao;

    /**
     * The Jdbc template.
     */
    @Resource
    protected JdbcTemplate jdbcTemplate;

    /**
     * Save.
     *
     * @param name the name
     */
    protected void save(String name) {
        SampleJpa sampleJpa = new SampleJpa();
        sampleJpa.setName(name);
        sampleJpaDao.save(sampleJpa);
    }

    /**
     * Save use jdbc.
     *
     * @param name the name
     */
    protected void saveUseJDBC(String name) {
        jdbcTemplate.update("INSERT INTO sample_jpa(name) values (?)", new Object[]{name});
    }

    /**
     * Throw exception.
     *
     * @param throwException the throw exception
     */
    protected void throwException(boolean throwException) {
        if (throwException) {
            throw new RuntimeException("expect exception");
        }
    }

    /**
     * Register callback.
     *
     * @param serviceName the service name
     */
    protected void registerCallback(String serviceName) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                switch (status) {
                    case 0:
                        System.out.println(serviceName + "提交成功");
                        break;
                    case 1:
                        System.out.println(serviceName + "回滚");
                        break;
                    default:
                        System.out.println("未知");
                }
            }
        });
    }

    /**
     * Gets name.
     *
     * @param serverName the server name
     * @return the name
     */
    protected String getName(String serverName) {
        return serverName + " " + Thread.currentThread().getStackTrace()[2].getMethodName();
    }


}
