package com.lzy.demo.design.pattern.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private final List<Command> commands = new ArrayList<>();

    public void addCommands(Command command) {
        commands.add(command);
    }

    public void executeCommand() {
        for (Command command : commands) {
            command.execute();
        }
    }
}
