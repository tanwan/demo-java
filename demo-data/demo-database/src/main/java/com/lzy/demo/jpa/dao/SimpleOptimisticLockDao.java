package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SimpleOptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface SimpleOptimisticLockDao extends JpaRepository<SimpleOptimisticLock, Integer> {

    /**
     * OPTIMISTIC,乐观锁,本质使用@Version
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SimpleOptimisticLock u WHERE u.id = ?1")
    @Lock(LockModeType.OPTIMISTIC)
    SimpleOptimisticLock findByIdOptimistic(Integer id);

    /**
     * OPTIMISTIC_FORCE_INCREMENT,乐观锁,读取到实体后,在事务提交之前会对version进行+1
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SimpleOptimisticLock u WHERE u.id = ?1")
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    SimpleOptimisticLock findByIdOptimisticForceIncrement(Integer id);

    /**
     * PESSIMISTIC_FORCE_INCREMENT,悲观锁,同时使用@Version
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SimpleOptimisticLock u WHERE u.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    SimpleOptimisticLock findByIdPessimisticForceIncrement(Integer id);

}
