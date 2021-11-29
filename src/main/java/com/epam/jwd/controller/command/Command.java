package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public interface Command {

    ResponseContext execute(RequestContext context);

    static Command of(String commandName) {
        return Commands.of(commandName);
    }
}
