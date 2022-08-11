package com.lzy.demo.design.pattern.prototype;

/**
 * 原型模式
 * 用原型实例指定创建对象的种类,并通过拷贝这个原型创建新的对象
 * 可以采用自己实现克隆方法,也可以采用Java的Object#clone方法,都有深复制和浅复制的问题
 *
 * @author LZY
 * @version v1.0
 */
public class ConcretePrototype implements Prototype, Cloneable {
    private String name;

    public ConcretePrototype(String name) {
        this.name = name;
    }

    @Override
    public <T extends Prototype> T copy() {
        //这里采用的是new一个对象
        //也可以使用Object#clone,不过要注意深复制和浅复制的问题
        ConcretePrototype concretePrototype = new ConcretePrototype(this.name);
        return (T) concretePrototype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
