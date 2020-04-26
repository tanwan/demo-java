/*
 * Created by lzy on 9/4/17.
 */
package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.dao.SimpleLockDao;
import com.lzy.demo.jpa.dao.SimpleOptimisticLockDao;
import com.lzy.demo.jpa.entity.SimpleLock;
import com.lzy.demo.jpa.entity.SimpleOptimisticLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Random;

/**
 * The type Demo lock service.
 *
 * @author lzy
 * @version v1.0
 */
@Service
public class SimpleLockService {
    @Resource
    private SimpleOptimisticLockDao simpleOptimisticLockDao;

    @Resource
    private SimpleLockDao simpleLockDao;

    /**
     * 使用LockModeType.OPTIMISTIC,乐观锁读,本质使用@Version,需要事务
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void optimisticForRead(String threadName) {
        SimpleOptimisticLock simpleOptimisticLock = simpleOptimisticLockDao.findByIdOptimistic(1);
        readWait(threadName, simpleOptimisticLock);
    }

    /**
     * 使用LockModeType.OPTIMISTIC_FORCE_INCREMENT,读取到实体后,在事务提交之前会对version进行+1
     * 因此,如果有多个读线程同时读取同一个实体时,在事务提交前都会去更新version,这时候会抛出异常,也就是无法并发读
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void optimisticForceIncrementForRead(String threadName) {
        SimpleOptimisticLock simpleOptimisticLock = simpleOptimisticLockDao.findByIdOptimisticForceIncrement(1);
        readWait(threadName, simpleOptimisticLock);
    }


    /**
     * 使用LockModeType.PESSIMISTIC_WRITE,悲观读锁,实体不需要@Version,本质上使用select for share
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticReadForRead(String threadName) {
        SimpleLock simpleLock = simpleLockDao.findByIdPessimisticRead(1);
        readWait(threadName, simpleLock);
    }

    /**
     * 使用LockModeType.PESSIMISTIC_WRITE,悲观读锁,读取后进行写入,实体不需要@Version,本质上使用select for share
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticReadForWrite() {
        SimpleLock simpleLock = simpleLockDao.findByIdPessimisticRead(1);
        writeWait(simpleLock);
    }


    /**
     * 使用LockModeType.PESSIMISTIC_WRITE,悲观写锁,实体不需要@Version,本质上使用select for update
     *
     * @param thread the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticWriteForRead(String thread) {
        SimpleLock simpleLock = simpleLockDao.findByIdPessimisticWrite(1);
        readWait(thread, simpleLock);
    }


    /**
     * 使用LockModeType.PESSIMISTIC_FORCE_INCREMENT,悲观锁,本质是使用select for update nowait同时使用@Version
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticForceIncrementForRead(String threadName) {
        SimpleOptimisticLock simpleOptimisticLock = simpleOptimisticLockDao.findByIdPessimisticForceIncrement(1);
        readWait(threadName, simpleOptimisticLock);
    }


    /**
     * 普通更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOptimisticLock() {
        SimpleOptimisticLock simpleOptimisticLock = simpleOptimisticLockDao.findById(1).get();
        writeWait(simpleOptimisticLock);
    }

    /**
     * 普通更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLock() {
        SimpleLock simpleLock = simpleLockDao.findById(1).get();
        writeWait(simpleLock);
    }

    /**
     * 模拟耗时处理
     */
    private void readWait(String threadName, SimpleLock simpleLock) {
        System.out.println(threadName + ": " + LocalTime.now() + ": " + simpleLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟耗时处理
     */
    private void readWait(String threadName, SimpleOptimisticLock simpleOptimisticLock) {
        System.out.println(threadName + ": " + LocalTime.now() + ": " + simpleOptimisticLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟耗时处理
     */
    private void writeWait(SimpleOptimisticLock simpleOptimisticLock) {
        System.out.println("write before: " + LocalTime.now() + ": " + simpleOptimisticLock);
        simpleOptimisticLock.setName("write" + new Random().nextInt(100));
        simpleOptimisticLockDao.saveAndFlush(simpleOptimisticLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("write update after: " + LocalTime.now() + ": " + simpleOptimisticLock);
    }

    /**
     * 模拟耗时处理
     */
    private void writeWait(SimpleLock simpleLock) {
        System.out.println("write before: " + LocalTime.now() + ": " + simpleLock);
        simpleLock.setName("write" + new Random().nextInt(100));
        simpleLockDao.saveAndFlush(simpleLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("write update after: " + LocalTime.now() + ": " + simpleLock);
    }
}
