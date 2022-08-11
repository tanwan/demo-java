package com.lzy.demo.design.pattern;


import com.lzy.demo.design.pattern.observer.ConcreteObserver1;
import com.lzy.demo.design.pattern.observer.ConcreteObserver2;
import com.lzy.demo.design.pattern.observer.ConcreteSubject;
import com.lzy.demo.design.pattern.observer.Subject;
import org.junit.jupiter.api.Test;

/**
 * 测试观察者
 *
 * @author LZY
 * @version v1.0
 */
public class ObserverTest {

    /**
     * 测试观察者
     */
    @Test
    public void testObserver() {
        Subject subject = new ConcreteSubject();
        subject.attach(new ConcreteObserver1());
        subject.attach(new ConcreteObserver2());

        subject.notifyAllObservers();
    }
}
