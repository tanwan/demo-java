package com.lzy.demo.design.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

/**
 * 懒加载的类,类似hibernate获取关联关系
 *
 * @author LZY
 * @version v1.0
 */
public class LazyLoaderBean {
    private static Enhancer enhancer = new Enhancer();
    //这个属性是懒加载的
    private LazyLoaderProperty lazyLoaderProperty;

    public LazyLoaderBean() {
        System.out.println("LazyLoaderBean()");
        // 为LazyLoaderProperty赋值
        this.lazyLoaderProperty = (LazyLoaderProperty) enhancer.create(LazyLoaderProperty.class, new LazyLoaderCreator());
    }

    public static class LazyLoaderCreator implements LazyLoader {
        @Override
        public Object loadObject() throws Exception {
            System.out.println("loadObject()");
            LazyLoaderProperty lazyLoaderProperty = new LazyLoaderProperty();
            lazyLoaderProperty.setName("hello world");
            return lazyLoaderProperty;
        }
    }

    public LazyLoaderProperty getLazyLoaderProperty() {
        return lazyLoaderProperty;
    }

    public void setLazyLoaderProperty(LazyLoaderProperty lazyLoaderProperty) {
        this.lazyLoaderProperty = lazyLoaderProperty;
    }

    public static class LazyLoaderProperty {
        public LazyLoaderProperty() {
            System.out.println(this.getClass());
        }

        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
