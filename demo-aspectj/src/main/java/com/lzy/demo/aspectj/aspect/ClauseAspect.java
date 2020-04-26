/*
 * Created by lzy on 2019-04-03 19:57.
 */
package com.lzy.demo.aspectj.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * aspect默认的实例化模型为单例
 * 这边使用了perthis,也就是这个切面会为符合perthis指定的切点(this(com.lzy.demo.aspectj.bean.SimpleAspect))
 * 的aop对象创建不同的实例
 * @author lzy
 * @version v1.0
 */
@Aspect("perthis(this(com.lzy.demo.aspectj.bean.SimpleAspect))")
public class ClauseAspect {


    /**
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void clause())")
    public void execution(ProceedingJoinPoint pjp) throws Throwable {
        // ClauseAspect会有不同的实例
        System.out.println(this);
        System.out.println("execution before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("execution after");
    }
}
