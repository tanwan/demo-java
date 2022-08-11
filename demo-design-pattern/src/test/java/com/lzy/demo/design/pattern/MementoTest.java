package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.memento.Caretaker;
import com.lzy.demo.design.pattern.memento.Originator;
import org.junit.jupiter.api.Test;

/**
 * 测试备忘录
 *
 * @author lzy
 * @version v1.0
 */
public class MementoTest {
    /**
     * 测试备忘录
     */
    @Test
    public void testMemento() {
        Originator originator = new Originator();
        Caretaker careTaker = new Caretaker();
        originator.setState("1");
        careTaker.add(originator.saveStateToMemento());
        originator.setState("2");
        careTaker.add(originator.saveStateToMemento());
        originator.setState("3");

        System.out.println("Current State: " + originator.getState());
        originator.getStateFromMemento(careTaker.get(0));
        System.out.println("First State: " + originator.getState());
        originator.getStateFromMemento(careTaker.get(1));
        System.out.println("Second State: " + originator.getState());
    }
}
