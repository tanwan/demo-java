/*
 * Created by LZY on 2017/6/11 21:22.
 */
package com.lzy.demo.concurrent.base;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * fork join 任务
 *
 * @author LZY
 * @version v1.0
 * @see RecursiveTask 有返回值的
 * @see java.util.concurrent.RecursiveAction 没有返回值的
 */
public class ForkJoinTest {
    //最大任务数
    private static final int MAX_TASK = 8;


    /**
     * 测试fork join
     *
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testForkJoin() throws Exception {
        List<Integer> list = IntStream.range(1, 101).boxed().collect(Collectors.toList());
        int sizeThread = (int) Math.ceil(1.0 * list.size() / MAX_TASK);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinSample forkJoinSample = new ForkJoinSample(list, sizeThread);
        Future<Integer> result = forkJoinPool.submit(forkJoinSample);
        System.out.println(result.get());
    }

    private static class ForkJoinSample extends RecursiveTask<Integer> {

        private static Logger logger = LoggerFactory.getLogger(ForkJoinSample.class);

        private List<Integer> list;


        //每个任务需要处理的元素个数
        private int sizeTask;

        /**
         * Instantiates a new Fork join demo.
         *
         * @param list     the list
         * @param sizeTask the size thread
         */
        public ForkJoinSample(List<Integer> list, int sizeTask) {
            this.list = list;
            this.sizeTask = sizeTask;
        }

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         * @see java.util.stream.AbstractTask#compute()
         * java.util.stream.AbstractTask#compute()并不是使用join来完成任务
         * 它的任务其实是一个二叉树,使用循环来拆分子任务,然后对二叉树的叶子节点做计算,
         * 对非叶子节点做合并计算。
         */
        @Override
        protected Integer compute() {
            Integer sum;
            //
            if (this.list.size() > sizeTask) {
                //大于就拆分任务
                int middle = list.size() / 2;
                ForkJoinSample leftTask = new ForkJoinSample(list.subList(0, middle), this.sizeTask);
                ForkJoinSample rightTask = new ForkJoinSample(list.subList(middle, this.list.size()), this.sizeTask);
                //只要调用fork方法,任务就会拆解,并执行子任务的该方法
                leftTask.fork();
                rightTask.fork();
                logger.info("fork");
                Integer leftResult = leftTask.join();
                Integer rightResult = rightTask.join();
                sum = leftResult + rightResult;
            } else {
                //小于就计算结果
                logger.info("compute");
                sum = list.stream().reduce(0, Integer::sum);
            }
            return sum;
        }
    }
}
