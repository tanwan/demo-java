/*
 * Created by lzy on 9/4/17.
 */
package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SampleLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

/**
 * @author lzy
 * @version v1.0
 */
public interface SampleLockDao extends JpaRepository<SampleLock, Integer> {
    /**
     * PESSIMISTIC_READ,悲观读锁,实体不需要@Version,本质上使用select for share
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SampleLock u WHERE u.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_READ)
    SampleLock findByIdPessimisticRead(Integer id);

    /**
     * PESSIMISTIC_WRITE,悲观写锁,实体不需要@Version,本质上使用select for update
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SampleLock u WHERE u.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SampleLock findByIdPessimisticWrite(Integer id);

}
