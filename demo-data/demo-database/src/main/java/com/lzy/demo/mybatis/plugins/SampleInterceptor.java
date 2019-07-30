/*
 * Created by lzy on 2019-07-27 14:35.
 */
package com.lzy.demo.mybatis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 拦截器
 *
 * @author lzy
 * @version v1.0
 */
@Intercepts({@Signature(
        // 可以拦截以下几种类型Executor,ParameterHandler,ResultSetHandler,StatementHandler
        type = Executor.class,
        // 这边的方法就是指定type的方法,比如query是Executor的一个方法,args,就是这个方法的属性
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class SampleInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("SampleInterceptor#intercept");
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {
        //mybatis-config.xml配置plugin的属性会传到这里
       System.out.println(properties);
    }
}
