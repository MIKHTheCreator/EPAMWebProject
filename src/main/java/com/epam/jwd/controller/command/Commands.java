package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.controller.command.impl.show_page.*;
import com.epam.jwd.dao.entity.user_account.Role;

import java.util.Arrays;
import java.util.List;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    SAVE_CLIENT_COMMAND(SaveClientCommand.getInstance(), Role.UNAUTHORIZED),
    SAVE_USER_COMMAND(SaveUserCommand.getInstance(), Role.UNAUTHORIZED),
    EDIT_USER_COMMAND(EditUserCommand.getInstance(), Role.USER, Role.ADMIN),
    SAVE_PASSPORT_COMMAND(SavePassportCommand.getInstance(), Role.USER, Role.ADMIN),
    LOG_OUT_USER_COMMAND(LogOutUserCommand.getInstance(), Role.USER, Role.ADMIN),
    CHANGE_LANGUAGE_COMMAND(ChangeLanguageCommand.getInstance()),
    AUTHORIZATION_COMMAND(AuthorisationCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_LOGIN_PAGE_COMMAND(ShowLogInPageCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_REGISTRATION_PAGE_COMMAND(ShowRegistrationPageCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_ERROR_PAGE_COMMAND(ShowErrorPageCommand.getInstance()),
    SHOW_EDIT_USER_INFO_PAGE_COMMAND(ShowEditUserInfoPageCommand.getInstance(), Role.USER, Role.ADMIN),
    SHOW_USER_INFO_PAGE_COMMAND(ShowUserInfoPageCommand.getInstance(), Role.USER, Role.ADMIN),
    SHOW_MAIN_PAGE_COMMAND(ShowMainPageCommand.getInstance());

    private final Command command;
    private final List<Role> allowedRoles;

    Commands(Command command, Role... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public Command getCommand() {
        return command;
    }

    public List<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public static Command of(String commandName) {
        return Arrays.stream(Commands.values())
                .filter(command -> command.name().equalsIgnoreCase(commandName))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }
}
