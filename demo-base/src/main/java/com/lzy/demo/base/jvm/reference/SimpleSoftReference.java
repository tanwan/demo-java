package com.lzy.demo.base.jvm.reference;

import java.lang.ref.SoftReference;

/**
 * 软引用的例子
 * 适用于创建缓存
 *
 * @author LZY
 * @version v1.0
 */
public class SimpleSoftReference {
    public static final int _1MB = 1024 * 1024;
    /**
     * 这个引用的对象,在系统将要发生内存溢出异常之前,会把这些对象列入回收范围之中并进行第二次回收
     */
    private SoftReference<byte[]> softReference;

    public byte[] getSoftReferenceData() {
        //因为这个数据可能会被GC回收掉,因此在获取的时候需要做出判断
        if (softReference == null || softReference.get() == null) {
            System.out.println("new SoftReference");
            softReference = new SoftReference<>(new byte[4 * _1MB]);
        }
        return softReference.get();
    }

}
