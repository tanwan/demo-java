/*
 * Created by lzy on 2019-07-29 08:40.
 */
package com.lzy.demo.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义注入方法配置
 * 这边需要继承DefaultSqlInjector,否则DefaultSqlInjector原有的sql将用不了
 *
 * @author lzy
 * @version v1.0
 * @see DefaultSqlInjector
 */
@Component
public class CustomSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new CustomInjectorMethod());
        return methodList;
    }
}
