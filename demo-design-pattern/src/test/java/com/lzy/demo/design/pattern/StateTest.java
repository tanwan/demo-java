package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.state.ConcreteState1;
import com.lzy.demo.design.pattern.state.ConcreteState2;
import com.lzy.demo.design.pattern.state.Context;
import com.lzy.demo.design.pattern.state.State;
import org.junit.jupiter.api.Test;

/**
 * 测试状态模式
 *
 * @author lzy
 * @version v1.0
 */
public class StateTest {
    /**
     * 测试状态模式
     */
    @Test
    public void testState() {
        Context context = new Context();

        State state1 = new ConcreteState1();
        state1.doAction(context);
        System.out.println(context.getState());

        State state2 = new ConcreteState2();
        state2.doAction(context);

        System.out.println(context.getState());
    }
}
