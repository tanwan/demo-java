package com.lzy.demo.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * aspect默认的实例化模型为单例,所以这边需要使用prototype
 * 这边使用了perthis,也就是这个切面会为符合perthis指定的切点(this(com.lzy.demo.spring.aop.bean.SimpleAspectBean))的aop对象创建不同的实例
 *
 * @author lzy
 * @version v1.0
 */
@Aspect("perthis(this(com.lzy.demo.spring.aop.bean.SimpleBeanInterface))")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
