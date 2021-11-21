package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.AuthorisationCommand;
import com.epam.jwd.controller.command.impl.BlockUsersBankAccountCommand;
import com.epam.jwd.controller.command.impl.ChangeLanguageCommand;
import com.epam.jwd.controller.command.impl.DefaultCommand;
import com.epam.jwd.controller.command.impl.EditUserCommand;
import com.epam.jwd.controller.command.impl.LogOutUserCommand;
import com.epam.jwd.controller.command.impl.SaveClientCommand;
import com.epam.jwd.controller.command.impl.SaveCreditCardCommand;
import com.epam.jwd.controller.command.impl.SavePassportCommand;
import com.epam.jwd.controller.command.impl.SavePaymentCommand;
import com.epam.jwd.controller.command.impl.SaveUserCommand;
import com.epam.jwd.controller.command.impl.ShowCreditCardsCommand;
import com.epam.jwd.controller.command.impl.ShowUsersCommand;
import com.epam.jwd.controller.command.impl.ShowUsersCreditCardsCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowAddCreditCardPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowCreditCardPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowEditUserInfoPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowErrorPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowLogInPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowMainPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowPaymentsPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowRegistrationPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowUserInfoPageCommand;
import com.epam.jwd.controller.command.impl.show_page.ShowUsersPageCommand;
import com.epam.jwd.dao.entity.user_account.Role;

import java.util.Arrays;
import java.util.List;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    REGISTRATION(SaveClientCommand.getInstance(), Role.UNAUTHORIZED),
    SAVE_USER_COMMAND(SaveUserCommand.getInstance(), Role.UNAUTHORIZED),
    EDIT_USER_COMMAND(EditUserCommand.getInstance(), Role.USER, Role.ADMIN),
    SAVE_PASSPORT_COMMAND(SavePassportCommand.getInstance(), Role.USER, Role.ADMIN),
    LOGOUT(LogOutUserCommand.getInstance(), Role.USER, Role.ADMIN),
    CHANGE_LANGUAGE_COMMAND(ChangeLanguageCommand.getInstance()),
    LOGIN(AuthorisationCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_LOGIN_PAGE_COMMAND(ShowLogInPageCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_REGISTRATION_PAGE_COMMAND(ShowRegistrationPageCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_ERROR_PAGE_COMMAND(ShowErrorPageCommand.getInstance()),
    SHOW_EDIT_USER_INFO_PAGE_COMMAND(ShowEditUserInfoPageCommand.getInstance(), Role.USER, Role.ADMIN),
    SHOW_USER_INFO_PAGE_COMMAND(ShowUserInfoPageCommand.getInstance(), Role.USER, Role.ADMIN),
    SHOW_MAIN_PAGE_COMMAND(ShowMainPageCommand.getInstance()),
    SAVE_CREDIT_CARD_COMMAND(SaveCreditCardCommand.getInstance(), Role.USER),
    SHOW_ADD_CREDIT_CARD_PAGE_COMMAND(ShowAddCreditCardPageCommand.getInstance(), Role.USER),
    SHOW_CREDIT_CARD_PAGE_COMMAND(ShowCreditCardPageCommand.getInstance(), Role.USER),
    SHOW_PAYMENTS_PAGE_COMMAND(ShowPaymentsPageCommand.getInstance(), Role.USER),
    SHOW_USERS_PAGE_COMMAND(ShowUsersPageCommand.getInstance(), Role.ADMIN),
    BLOCK_USERS_BANK_ACCOUNT_COMMAND(BlockUsersBankAccountCommand.getInstance(), Role.ADMIN),
    SAVE_PAYMENT_COMMAND(SavePaymentCommand.getInstance(), Role.USER),
    SHOW_CREDIT_CARDS_COMMAND(ShowCreditCardsCommand.getInstance(), Role.ADMIN),
    SHOW_USERS_COMMAND(ShowUsersCommand.getInstance(), Role.ADMIN),
    SHOW_USERS_CREDIT_CARDS_COMMAND(ShowUsersCreditCardsCommand.getInstance(), Role.ADMIN);

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

    public static Commands getCommands(String commandName) {
        for (Commands command : values()) {
            if (command.name().equalsIgnoreCase(commandName)) {
                return command;
            }
        }
        return DEFAULT;
    }
}
