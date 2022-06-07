package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.dao.SimpleJpaDao;
import com.lzy.demo.jpa.entity.SimpleJpa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

public abstract class AbstractTransactionService {

    @Resource
    protected SimpleJpaDao simpleJpaDao;

    @Resource
    protected JdbcTemplate jdbcTemplate;

    protected void save(String name) {
        SimpleJpa simpleJpa = new SimpleJpa();
        simpleJpa.setName(name);
        simpleJpaDao.save(simpleJpa);
    }

    /**
     * Save use jdbc.
     *
     * @param name the name
     */
    protected void saveUseJDBC(String name) {
        jdbcTemplate.update("INSERT INTO simple_jpa(name) values (?)", new Object[]{name});
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
