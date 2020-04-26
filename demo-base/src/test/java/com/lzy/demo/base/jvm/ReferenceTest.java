/*
 * Created by lzy on 2019-08-01 20:06.
 */
package com.lzy.demo.base.jvm;

import com.lzy.demo.base.jvm.reference.SimpleSoftReference;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 引用测试
 *
 * @author lzy
 * @version v1.0
 */
public class ReferenceTest {

    /**
     * VM参数 -Xmx11M -Xms11M  -XX:+PrintGCDetails  -Xmn10M -XX:SurvivorRatio=8
     */
    @Test
    public void testSoftReference() {
        SimpleSoftReference simpleSoftReference = new SimpleSoftReference();
        System.out.println("data length: " + simpleSoftReference.getSoftReferenceData().length);
        if (simpleSoftReference.getSoftReferenceData().length > 0) {
            List<byte[]> list = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                System.out.println("new byte[]");
                list.add(new byte[SimpleSoftReference._1MB]);
            }
        }
        System.out.println("data length: " + simpleSoftReference.getSoftReferenceData().length);
    }
}
