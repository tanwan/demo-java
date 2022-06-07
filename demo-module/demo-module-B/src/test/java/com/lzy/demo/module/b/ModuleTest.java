package com.lzy.demo.module.b;

import com.lzy.demo.module.a.opens.ModuleAService;
import com.lzy.demo.module.a.opens.UseService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.util.ServiceLoader;

/**
 * 模块测试,如果启动发现报kotlin错误,Build->rebuild project
 *
 * @author lzy
 * @version v1.0
 */
public class ModuleTest {


    /**
     * 测试模块化
     */
    @Test
    public void testUseModule() {
        ModuleAService moduleAService = new ModuleAService();
        System.out.println(moduleAService.moduleAService());
    }

    /**
     * 测试opens
     *
     * @throws Exception the exception
     */
    @Test
    public void testOpens() throws Exception {
        //没有opens
        Constructor ownerConstructor = Class.forName("com.lzy.demo.module.a.owner.OwnerClass").getDeclaredConstructor();
        //没有open的,无法反射非public的
        Assertions.assertThatCode(() -> ownerConstructor.setAccessible(true)).isInstanceOf(InaccessibleObjectException.class);

        //使用opens
        Constructor openConstructor = Class.forName("com.lzy.demo.module.a.opens.OpenClass").getDeclaredConstructor();
        openConstructor.setAccessible(true);
        Object object = openConstructor.newInstance();
        Method method = object.getClass().getDeclaredMethod("privateMethod");
        method.setAccessible(true);
        System.out.println(method.invoke(object));
    }

    /**
     * 测试使用uses,provider
     */
    @Test
    public void testUse() {
        //在module-info.java使用了uses com.lzy.demo.module.a.opens.UseService,则UseService可以通过ServiceLoader.load加载
        ServiceLoader<UseService> serviceLoader = ServiceLoader.load(UseService.class);
        serviceLoader.stream().forEach(service -> System.out.println(service.get().useService()));
    }
}
