package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SimpleLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

public interface SimpleLockDao extends JpaRepository<SimpleLock, Integer> {
    /**
     * PESSIMISTIC_READ,悲观读锁,实体不需要@Version,本质上使用select for share
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SimpleLock u WHERE u.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_READ)
    SimpleLock findByIdPessimisticRead(Integer id);

    /**
     * PESSIMISTIC_WRITE,悲观写锁,实体不需要@Version,本质上使用select for update
     *
     * @param id the id
     * @return the user
     */
    @Query("SELECT u FROM SimpleLock u WHERE u.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SimpleLock findByIdPessimisticWrite(Integer id);

}
