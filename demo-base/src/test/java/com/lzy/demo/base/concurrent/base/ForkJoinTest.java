package com.lzy.demo.base.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
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
     * @throws Exception the exception
     */
    @Test
    public void testForkJoin() throws Exception {
        List<Integer> list = IntStream.range(1, 101).boxed().collect(Collectors.toList());
        int sizeThread = (int) Math.ceil(1.0 * list.size() / MAX_TASK);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SimpleForkJoin simpleForkJoin = new SimpleForkJoin(list, sizeThread);
        Future<Integer> result = forkJoinPool.submit(simpleForkJoin);
        System.out.println(result.get());
    }

    private static class SimpleForkJoin extends RecursiveTask<Integer> {

        private List<Integer> list;

        //每个任务需要处理的元素个数
        private int sizeTask;

        SimpleForkJoin(List<Integer> list, int sizeTask) {
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
                SimpleForkJoin leftTask = new SimpleForkJoin(list.subList(0, middle), this.sizeTask);
                SimpleForkJoin rightTask = new SimpleForkJoin(list.subList(middle, this.list.size()), this.sizeTask);
                //只要调用fork方法,任务就会拆解,并执行子任务的该方法
                leftTask.fork();
                rightTask.fork();
                System.out.println("fork");
                Integer leftResult = leftTask.join();
                Integer rightResult = rightTask.join();
                sum = leftResult + rightResult;
            } else {
                //小于就计算结果
                System.out.println("compute");
                sum = list.stream().reduce(0, Integer::sum);
            }
            return sum;
        }
    }
}
