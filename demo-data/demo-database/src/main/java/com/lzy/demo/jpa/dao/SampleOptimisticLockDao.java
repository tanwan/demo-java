/*
 * Created by lzy on 9/4/17.
 */
package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SampleOptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

/**
 * @author lzy
 * @version v1.0
 */
public interface SampleOptimisticLockDao extends JpaRepository<SampleOptimisticLock, Integer> {

    /**
     * OPTIMISTIC,乐观锁,本质使用@Version
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SampleOptimisticLock u WHERE u.id = ?1")
    @Lock(LockModeType.OPTIMISTIC)
    SampleOptimisticLock findByIdOptimistic(Integer id);

    /**
     * OPTIMISTIC_FORCE_INCREMENT,乐观锁,读取到实体后,在事务提交之前会对version进行+1
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SampleOptimisticLock u WHERE u.id = ?1")
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    SampleOptimisticLock findByIdOptimisticForceIncrement(Integer id);

    /**
     * PESSIMISTIC_FORCE_INCREMENT,悲观锁,同时使用@Version
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SampleOptimisticLock u WHERE u.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    SampleOptimisticLock findByIdPessimisticForceIncrement(Integer id);

}
