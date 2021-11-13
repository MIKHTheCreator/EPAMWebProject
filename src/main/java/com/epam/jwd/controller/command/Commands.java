package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.DefaultCommand;
import com.epam.jwd.controller.command.impl.SaveClientCommand;
import com.epam.jwd.controller.command.impl.SaveUserCommand;

import java.util.Arrays;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    SAVE_CLIENT_COMMAND(SaveClientCommand.getInstance()),
    SAVE_USER_COMMAND(SaveUserCommand.getInstance());

    private final Command command;

    Commands(Command command) {
        this.command = command;
    }

    static Command getCommand(String commandName) {
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(commandName))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }
}
