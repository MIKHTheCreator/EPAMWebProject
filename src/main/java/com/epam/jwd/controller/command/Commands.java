package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.DefaultCommand;
import com.epam.jwd.controller.command.impl.ErrorCommand;
import com.epam.jwd.controller.command.impl.ShowUsersCommand;

import java.util.Arrays;

public enum Commands {
    DEFAULT(DefaultCommand.getInstance()),
    ERROR(ErrorCommand.getInstance()),
    SHOW_USERS(ShowUsersCommand.getInstance()),
    BLOCK_USER(BlockUserCommand.getInstance()),
    SHOW_CREDIT_CARDS(ShowCreditCardsCommand.getInstance()),
    DELETE_CREDIT_CARD(DeleteCreditCardCommand.getInstance()),
    SHOW_PAYMENTS(ShowPaymentsCommand.getInstance()),
    SHOW_PASSPORT_DATA(ShowPassportDataCommand.getInstance());

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
