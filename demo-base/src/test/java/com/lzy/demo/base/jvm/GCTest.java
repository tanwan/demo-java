package com.lzy.demo.base.jvm;


import org.junit.jupiter.api.Test;

public class GCTest {
    private static final int ONE_MB = 1024 * 1024;

    /**
     * 测试提前进入老年代
     * VM参数 -Xmx20M -Xms20M  -XX:+PrintGCDetails -Xmn10M -XX:SurvivorRatio=8
     */
    @Test
    public void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        System.out.println("allocation1");
        allocation1 = new byte[2 * ONE_MB];
        System.out.println("allocation2");
        allocation2 = new byte[2 * ONE_MB];
        System.out.println("allocation3");
        allocation3 = new byte[2 * ONE_MB];
        System.out.println("allocation4");
        //出现一次Minor GC
        //在给allocation4分配内存的时候,发现Eden剩余的空间不足以分配给allocation4了,因此发生MinorGC
        //GC期间又发现3个2MB大小的对象无法放入Survivor空间,所以只好提前转移到老年代
        allocation4 = new byte[4 * ONE_MB];
    }
}
