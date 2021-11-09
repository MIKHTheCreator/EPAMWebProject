package com.epam.jwd.controller.command;

import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.exception.ServiceException;

public interface Command {

    ResponseContext execute(RequestContext context) throws ServiceException;

    static Command of(String commandName) {
        return Commands.getCommand(commandName);
    }
}
