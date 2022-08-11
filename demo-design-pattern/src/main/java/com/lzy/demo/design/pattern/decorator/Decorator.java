package com.lzy.demo.design.pattern.decorator;

/**
 * 装饰器接口,继承自组件,又拥有组件的对象
 * 动态地给一个对象添加一些额外的职责.就增加功能来说，装饰模式比生成子类更为灵活
 *
 * @author LZY
 * @version v1.0
 */
public abstract class Decorator implements Component {

    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
