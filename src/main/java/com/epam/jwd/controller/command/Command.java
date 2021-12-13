package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

/**
 * Interface which provides command pattern methods for classes
 */
public interface Command {

    /**
     * Method which executes current command
     *
     * @param context {@link RequestContext}
     * @return {@link RequestContext}
     */
    ResponseContext execute(RequestContext context);

    /**
     * Method which gets command by its name from all amount of available commands
     *
     * @param commandName command name
     * @return command
     */
    static Command of(String commandName) {
        return Commands.of(commandName);
    }
}
