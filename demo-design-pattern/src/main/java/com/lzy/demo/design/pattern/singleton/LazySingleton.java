package com.lzy.demo.design.pattern.singleton;

/**
 * 懒汉式单例模式
 *
 * @author LZY
 * @version v1.0
 */
public final class LazySingleton {

    private static volatile LazySingleton instance = null;


    private LazySingleton() {
        System.out.println("LazySingleton()");
    }


    /**
     * 获取实例,双重检查
     *
     * @return the instance
     */
    public static LazySingleton getInstance() {
        //多个线程可能同时进入
        //instance用volatile修饰是为了禁止指令重排
        //类初始化大致步骤1.分配内存空间 2.初始化对象 3.设置instance指向分配的内存地址
        //2和3指令重排可以调换顺序
        //如果刚好顺序是 1->3->2,然后一个线程正在实例化,刚好到3,另一个线程刚好到这里执行判断
        //则这个判断直接返回false,因此另一个线程拿到的对象是未初始化的
        if (instance == null) {
            synchronized (LazySingleton.class) {
                //如果一个线程已经实例化之后,就要判断instance是否为空,否则又会再实例化一次
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
