package com.lzy.demo.aspectj.aspect;

/*
 * 使用aspectj的切面,此文件是个.aj文件
 */
public aspect AjAspect {

    //声明切点
    pointcut callPointcut(): call(void call());

    // 等价于void around():call(void call())
    void around(): callPointcut() {
        System.out.println("call before: " + thisJoinPoint.getSignature());
        // 使用proceed调用目标的方法
        proceed();
        System.out.println("call after");
    }

    // 连接点为void within(),并且该连接点属于SimpleAspectBean1
    void around(): execution(void within()) && within(com.lzy.demo.aspectj.bean.SimpleAspectBean1){
        System.out.println("within before: " + thisJoinPoint.getSignature());
        // 使用proceed调用目标的方法
        proceed();
        System.out.println("within after");
    }

    // before不能有返回值
    // 连接点为void targetArgs(),并且接入点的target对象属于SimpleAspect,并获取参数
    before(Object target): call(void targetArgs()) && target(com.lzy.demo.aspectj.bean.SimpleAspect) && target(target){
        System.out.println("targetArgs before: " + thisJoinPoint.getSignature());
        // 获取为到this
        System.out.println("this:" + thisJoinPoint.getThis());
        System.out.println("target:" + target.getClass());
    }

    //在方法返回结果后
    after() returning(Object result):execution(void *..SimpleAspectBean1.order()){
        System.out.println("after returning,result:" + result);
    }

    //在方法抛出异常后
    after() throwing(Exception e):execution(void *..SimpleAspectBean1.order()){
        System.out.println("after throwing,exception:" + e);
    }
}
