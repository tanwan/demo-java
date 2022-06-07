package com.lzy.demo.base.concurrent.lock;

import com.lzy.demo.base.ThreadEachCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

@ExtendWith(ThreadEachCallback.class)
public class CountDownLatchTest {

    /**
     * 测试CountDownLatch
     *
     * @param executorService executorService
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testCountDownLatch(@ThreadEachCallback.ThreadParam(5) ExecutorService executorService) throws InterruptedException {
        //可以看成有10个任务需要完成
        CountDownLatch countDownLatch = new CountDownLatch(10);
        IntStream.range(0, 10).forEach(i -> executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //每执行完一个任务,就减1
            System.out.println(LocalTime.now() + ":complete:" + i);
            countDownLatch.countDown();
        }));
        //在这个方法之后的代码,需要在countDownLatch为0的时候才会执行
        countDownLatch.await();
        System.out.println("main run complete");
    }
}
