package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.*;

import java.util.Arrays;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    SAVE_CLIENT_COMMAND(SaveClientCommand.getInstance()),
    SAVE_USER_COMMAND(SaveUserCommand.getInstance()),
    EDIT_USER_COMMAND(EditUserCommand.getInstance()),
    SAVE_PASSPORT_COMMAND(SavePassportCommand.getInstance()),
    LOG_OUT_USER_COMMAND(LogOutUserCommand.getInstance()),
    CHANGE_LANGUAGE_COMMAND(ChangeLanguageCommand.getInstance()),
    AUTHORIZATION_COMMAND(AuthorisationCommand.getInstance());

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
