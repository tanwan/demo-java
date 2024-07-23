package com.lzy.demo.design.pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

/**
 * jdk动态代理的异常处理
 *
 * @author LZY
 * @version v1.0
 */
public class ExceptionHandle {

    /**
     * jdk动态代理生成的代理类对异常的处理逻辑
     * 1. RuntimeException,接口已声明的异常,Error直接抛出
     * 不过因为代理类是使用method.invoke(target, args)来调用真正实例的,而method.invoke会抛出InvocationTargetException的异常
     * 2. 其它异常包装为UndeclaredThrowableException抛出
     */
    public interface Subject {

        /**
         * 声明了Exception, 如果异常属于Exception,会直接抛出
         *
         * @throws Exception e
         */
        void declareThrowException() throws Exception;

        /**
         * 声明了SQLException, 但是method.invoke(target, args)会抛出InvocationTargetException,所以最终会抛出UndeclaredThrowableException
         *
         * @throws SQLException e
         */
        void declareThrowSQLException() throws SQLException;

        /**
         * 声明了InvocationTargetException, 如果异常属于InvocationTargetException, 会直接抛出
         *
         * @throws InvocationTargetException e
         */
        void declareThrowInvocationTargetException() throws InvocationTargetException;

        /**
         * 在动态代理中抛出异常
         */
        void throwSQLExceptionInInvoke() throws IllegalAccessException;
    }


    public static class RealSubject implements Subject {

        @Override
        public void declareThrowException() throws Exception {
            throw new SQLException("declareThrowException");
        }

        @Override
        public void declareThrowSQLException() throws SQLException {
            throw new SQLException("declareThrowSQLException");
        }

        @Override
        public void declareThrowInvocationTargetException() throws InvocationTargetException {
            throw new InvocationTargetException(new SQLException("declareThrowInvocationTargetException"));
        }

        @Override
        public void throwSQLExceptionInInvoke() {
        }
    }

    public static class ExceptionHandleProxy implements InvocationHandler {
        private Object target;

        private boolean throwCause;

        ExceptionHandleProxy(Object target, boolean throwCause) {
            this.target = target;
            this.throwCause = throwCause;
        }

        public static Subject getProxy(Subject realSubject, boolean throwCause) {
            //只能代理接口,不能代理类
            return (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), new ExceptionHandleProxy(realSubject, throwCause));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("throwSQLExceptionInInvoke".equals(method.getName())) {
                throw new SQLException("throw SQLException in invoke");
            }
            if (throwCause) {
                try {
                    return method.invoke(target, args);
                } catch (InvocationTargetException e) {
                    // 抛出cause
                    throw e.getCause();
                }
            } else {
                return method.invoke(target, args);
            }
        }
    }
}
