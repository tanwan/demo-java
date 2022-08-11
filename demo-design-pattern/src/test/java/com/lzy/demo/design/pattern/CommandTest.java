package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.command.ConcreteCommand1;
import com.lzy.demo.design.pattern.command.ConcreteCommand2;
import com.lzy.demo.design.pattern.command.Invoker;
import com.lzy.demo.design.pattern.command.Receiver1;
import com.lzy.demo.design.pattern.command.Receiver2;
import org.junit.jupiter.api.Test;

/**
 * 测试命令模式
 *
 * @author lzy
 * @version v1.0
 */
public class CommandTest {

    /**
     * 测试命令模式
     */
    @Test
    public void testCommand() {
        Invoker invoker = new Invoker();
        ConcreteCommand1 command1 = new ConcreteCommand1(new Receiver1());
        ConcreteCommand2 command2 = new ConcreteCommand2(new Receiver2());

        invoker.addCommands(command1);
        invoker.addCommands(command2);

        invoker.executeCommand();
    }
}
