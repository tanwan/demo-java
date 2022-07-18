package com.lzy.demo.jpa;

import com.lzy.demo.jpa.dao.SimpleOptimisticLockDao;
import com.lzy.demo.jpa.entity.SimpleOptimisticLock;
import com.lzy.demo.jpa.service.SimpleLockService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@ActiveProfiles("jpa")
@Commit
public class LockTest {


    @Resource
    private SimpleOptimisticLockDao simpleOptimisticLockDao;

    @Resource
    private SimpleLockService simpleLockService;

    /**
     * 测试Version
     */
    @Test
    public void testVersion() {
        SimpleOptimisticLock simpleOptimisticLock = simpleOptimisticLockDao.findById(1).get();
        simpleOptimisticLock.setName("5");
        simpleOptimisticLockDao.save(simpleOptimisticLock);
    }

    /**
     * 测试LockModeType.OPTIMISTIC乐观锁,实体需要@Version
     * 读和读:都是可以并发的
     * 读和写:只要更新实体的version跟数据库该记录的version一致,就可以并发,否则抛出异常,更新的时候,会把version+1
     * 写和写:写入version就会+1,因此无法并发写写
     * 结果: 读和读,读和写(version一致)可以并发,以下2个写的线程有一个会抛出异常
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testOptimistic() throws InterruptedException {
        // 模拟一个读线程获取实体,获取实体后,会等待2秒模拟耗时操作
        new Thread(() -> simpleLockService.optimisticForRead("readThread1")).start();
        Thread.sleep(200);
        // 模拟另一个读线程获取实体
        new Thread(() -> assertThatCode(() -> simpleLockService.optimisticForRead("readThread2"))
                .doesNotThrowAnyException()).start();

        // 模拟一个写线程进行更新实体
        new Thread(() -> simpleLockService.updateOptimisticLock()).start();
        // 模拟另一个写线程进行更新实体
        new Thread(() -> simpleLockService.updateOptimisticLock()).start();
        Thread.sleep(5000);
    }

    /**
     * 测试LockModeType.OPTIMISTIC_FORCE_INCREMENT,乐观锁,实体需要@Version
     * 读和读:多个读线程同时读取同一个实体时,在事务提交前都会去更新version,这时候会抛出异常,也就是无法并发读
     * 读和写:读写线程同时存在时,在更新实体的时候,就会发生version不一致,也就是无法并发读写
     * 写和写:读和读都无法并发,肯定无法进行并发写写
     * 结果: 读和读,读和写都不能并发,以下3个线程,有2个线程会抛出异常
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testOptimisticForceIncrement() throws InterruptedException {
        // 模拟一个读线程获取实体,获取实体后,会等待2秒模拟耗时操作
        new Thread(() -> simpleLockService.optimisticForceIncrementForRead("readThread1")).start();
        Thread.sleep(200);
        // 模拟另一个读线程获取实体
        new Thread(() -> simpleLockService.optimisticForceIncrementForRead("readThread2")).start();

        // 模拟一个写线程进行更新实体
        new Thread(() -> simpleLockService.updateOptimisticLock()).start();
        Thread.sleep(5000);
    }


    /**
     * 测试LockModeType.PESSIMISTIC_READ,悲观读锁,实体不需要@Version,本质上使用select for share
     * 读和读: 可以并发
     * 读和写: 会阻塞,写线程需要等读线程提交后,才会执行更新,因此无法并发读写
     * 结果:读和读可以并发,读和写不能并发
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testPessimisticRead() throws InterruptedException {
        // 模拟一个读线程获取实体,获取实体后,会等待2秒模拟耗时操作
        new Thread(() -> simpleLockService.pessimisticReadForRead("read1")).start();
        Thread.sleep(200);
        // 模拟另一个读线程获取实体
        new Thread(() -> simpleLockService.pessimisticReadForRead("read2")).start();
        // 模拟一个写线程进行更新实体,这个线程更新成功后打印的时间比读线程多了4秒(如果不阻塞,应该是2秒)
        new Thread(() -> simpleLockService.updateLock()).start();
        Thread.sleep(5000);
    }

    /**
     * 测试LockModeType.PESSIMISTIC_READ并发写,悲观读锁,实体不需要@Version,本质上使用select for share
     * 写和写: 会发生死锁,无法并发写写
     * 结果:无法并发写写
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testPessimisticReadWrite() throws InterruptedException {
        // 模拟一个写线程进行更新实体
        new Thread(() -> simpleLockService.pessimisticReadForWrite()).start();
        // 模拟另一个写线程进行更新实体
        new Thread(() -> simpleLockService.pessimisticReadForWrite()).start();
        Thread.sleep(1000);
    }

    /**
     * 测试LockModeType.PESSIMISTIC_WRITE,悲观写锁,实体不需要@Version,本质上使用select for update
     * 读和读: 第二个读线程会进行阻塞,需要等第一个线程事务提交后释放锁才会继续执行
     * 读和写: 写线程会进行阻塞,需要等待读线程事务提交后释放锁才会继续执行
     * 写和写: 读和读都进行阻塞了,写写当然也得阻塞
     * 结果: 读读,读写,写写都无法并发执行
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testPessimisticWrite() throws InterruptedException {
        // 模拟一个读线程获取实体,获取实体后,会等待2秒模拟耗时操作
        new Thread(() -> simpleLockService.pessimisticWriteForRead("read1")
        ).start();
        Thread.sleep(200);
        // 模拟另一个读线程获取实体,这个线程打印的时间比前一个读线程多了2秒(因为阻塞)
        new Thread(() -> simpleLockService.pessimisticWriteForRead("read2")
        ).start();
        Thread.sleep(200);
        // 模拟一个写线程进行更新实体,这个线程更新成功后打印的时间比读线程多了4秒(如果不阻塞,应该是2秒)
        new Thread(() -> simpleLockService.updateLock()).start();
        Thread.sleep(8000);
    }


    /**
     * 测试LockModeType.PESSIMISTIC_FORCE_INCREMENT,悲观锁,同时还使用version
     * 读和读: 因为使用了select for update nowait,所以无法并发读读
     * 读和写: 无法并发
     * 写和写: 无法并发
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testPessimisticForceIncrement() throws InterruptedException {
        new Thread(() -> simpleLockService.pessimisticForceIncrementForRead("read1")).start();
        Thread.sleep(200);
        new Thread(() -> simpleLockService.pessimisticForceIncrementForRead("read2")).start();
        new Thread(() -> simpleLockService.updateOptimisticLock()).start();
        Thread.sleep(8000);
    }
}
