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

    /**
     * Instantiates a new Lazy loader bean.
     */
    public LazyLoaderBean() {
        System.out.println("LazyLoaderBean()");
        // 为LazyLoaderProperty赋值
        this.lazyLoaderProperty = (LazyLoaderProperty) enhancer.create(LazyLoaderProperty.class, new LazyLoaderCreator());
    }


    /**
     * The type Lazy loader creator.
     */
    public static class LazyLoaderCreator implements LazyLoader {
        /**
         * 在这里加载对象
         */
        @Override
        public Object loadObject() throws Exception {
            System.out.println("loadObject()");
            LazyLoaderProperty lazyLoaderProperty = new LazyLoaderProperty();
            lazyLoaderProperty.setName("hello world");
            return lazyLoaderProperty;
        }
    }

    /**
     * Gets lazy loader property.
     *
     * @return the lazy loader property
     */
    public LazyLoaderProperty getLazyLoaderProperty() {
        return lazyLoaderProperty;
    }

    /**
     * Sets lazy loader property.
     *
     * @param lazyLoaderProperty the lazy loader property
     */
    public void setLazyLoaderProperty(LazyLoaderProperty lazyLoaderProperty) {
        this.lazyLoaderProperty = lazyLoaderProperty;
    }

    /**
     * 懒加载
     */
    public static class LazyLoaderProperty {
        /**
         * Instantiates a new Lazy loader property.
         */
        public LazyLoaderProperty() {
            System.out.println(this.getClass());
        }

        private String name;

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets name.
         *
         * @param name the name
         */
        public void setName(String name) {
            this.name = name;
        }
    }
}
