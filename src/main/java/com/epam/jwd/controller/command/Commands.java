package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.dao.entity.user_account.Role;

import java.util.Arrays;
import java.util.List;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    SAVE_CLIENT_COMMAND(SaveClientCommand.getInstance()),
    SAVE_USER_COMMAND(SaveUserCommand.getInstance()),
    EDIT_USER_COMMAND(EditUserCommand.getInstance()),
    SAVE_PASSPORT_COMMAND(SavePassportCommand.getInstance()),
    LOG_OUT_USER_COMMAND(LogOutUserCommand.getInstance()),
    CHANGE_LANGUAGE_COMMAND(ChangeLanguageCommand.getInstance()),
    AUTHORIZATION_COMMAND(AuthorisationCommand.getInstance()),
    SHOW_LOGIN_PAGE_COMMAND(ShowLogInPageCommand.getInstance()),
    SHOW_REGISTRATION_PAGE_COMMAND(ShowRegistrationPageCommand.getInstance());

    private final Command command;
    private final List<Role> allowedRoles;

    Commands(Command command, Role... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    static Command getCommand(String commandName) {
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(commandName))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }
}
