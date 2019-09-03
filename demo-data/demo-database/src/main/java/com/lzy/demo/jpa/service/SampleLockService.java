/*
 * Created by lzy on 9/4/17.
 */
package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.dao.SampleLockDao;
import com.lzy.demo.jpa.dao.SampleOptimisticLockDao;
import com.lzy.demo.jpa.entity.SampleLock;
import com.lzy.demo.jpa.entity.SampleOptimisticLock;
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
public class SampleLockService {
    @Resource
    private SampleOptimisticLockDao sampleOptimisticLockDao;

    @Resource
    private SampleLockDao sampleLockDao;

    /**
     * 使用LockModeType.OPTIMISTIC,乐观锁读,本质使用@Version,需要事务
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void optimisticForRead(String threadName) {
        SampleOptimisticLock sampleOptimisticLock = sampleOptimisticLockDao.findByIdOptimistic(1);
        readWait(threadName, sampleOptimisticLock);
    }

    /**
     * 使用LockModeType.OPTIMISTIC_FORCE_INCREMENT,读取到实体后,在事务提交之前会对version进行+1
     * 因此,如果有多个读线程同时读取同一个实体时,在事务提交前都会去更新version,这时候会抛出异常,也就是无法并发读
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void optimisticForceIncrementForRead(String threadName) {
        SampleOptimisticLock sampleOptimisticLock = sampleOptimisticLockDao.findByIdOptimisticForceIncrement(1);
        readWait(threadName, sampleOptimisticLock);
    }


    /**
     * 使用LockModeType.PESSIMISTIC_WRITE,悲观读锁,实体不需要@Version,本质上使用select for share
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticReadForRead(String threadName) {
        SampleLock sampleLock = sampleLockDao.findByIdPessimisticRead(1);
        readWait(threadName, sampleLock);
    }

    /**
     * 使用LockModeType.PESSIMISTIC_WRITE,悲观读锁,读取后进行写入,实体不需要@Version,本质上使用select for share
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticReadForWrite() {
        SampleLock sampleLock = sampleLockDao.findByIdPessimisticRead(1);
        writeWait(sampleLock);
    }


    /**
     * 使用LockModeType.PESSIMISTIC_WRITE,悲观写锁,实体不需要@Version,本质上使用select for update
     *
     * @param thread the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticWriteForRead(String thread) {
        SampleLock sampleLock = sampleLockDao.findByIdPessimisticWrite(1);
        readWait(thread, sampleLock);
    }


    /**
     * 使用LockModeType.PESSIMISTIC_FORCE_INCREMENT,悲观锁,本质是使用select for update nowait同时使用@Version
     *
     * @param threadName the thread
     */
    @Transactional(rollbackFor = Exception.class)
    public void pessimisticForceIncrementForRead(String threadName) {
        SampleOptimisticLock sampleOptimisticLock = sampleOptimisticLockDao.findByIdPessimisticForceIncrement(1);
        readWait(threadName, sampleOptimisticLock);
    }


    /**
     * 普通更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOptimisticLock() {
        SampleOptimisticLock sampleOptimisticLock = sampleOptimisticLockDao.findById(1).get();
        writeWait(sampleOptimisticLock);
    }

    /**
     * 普通更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLock() {
        SampleLock sampleLock = sampleLockDao.findById(1).get();
        writeWait(sampleLock);
    }

    /**
     * 模拟耗时处理
     */
    private void readWait(String threadName, SampleLock sampleLock) {
        System.out.println(threadName + ": " + LocalTime.now() + ": " + sampleLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟耗时处理
     */
    private void readWait(String threadName, SampleOptimisticLock sampleOptimisticLock) {
        System.out.println(threadName + ": " + LocalTime.now() + ": " + sampleOptimisticLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟耗时处理
     */
    private void writeWait(SampleOptimisticLock sampleOptimisticLock) {
        System.out.println("write before: " + LocalTime.now() + ": " + sampleOptimisticLock);
        sampleOptimisticLock.setName("write" + new Random().nextInt(100));
        sampleOptimisticLockDao.saveAndFlush(sampleOptimisticLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("write update after: " + LocalTime.now() + ": " + sampleOptimisticLock);
    }

    /**
     * 模拟耗时处理
     */
    private void writeWait(SampleLock sampleLock) {
        System.out.println("write before: " + LocalTime.now() + ": " + sampleLock);
        sampleLock.setName("write" + new Random().nextInt(100));
        sampleLockDao.saveAndFlush(sampleLock);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("write update after: " + LocalTime.now() + ": " + sampleLock);
    }
}
