package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.mediator.Colleague;
import com.lzy.demo.design.pattern.mediator.ConcreteColleague1;
import com.lzy.demo.design.pattern.mediator.ConcreteColleague2;
import com.lzy.demo.design.pattern.mediator.ConcreteMediator;
import com.lzy.demo.design.pattern.mediator.Mediator;
import org.junit.jupiter.api.Test;

/**
 * 测试中介者
 *
 * @author lzy
 * @version v1.0
 */
public class MediatorTest {

    /**
     * 测试中介者
     */
    @Test
    public void testMediator() {
        Mediator mediator = new ConcreteMediator();
        Colleague colleague1 = new ConcreteColleague1(mediator);
        Colleague colleague2 = new ConcreteColleague2(mediator);
        mediator.add(colleague1);
        mediator.add(colleague2);

        colleague1.changed();
    }
}
