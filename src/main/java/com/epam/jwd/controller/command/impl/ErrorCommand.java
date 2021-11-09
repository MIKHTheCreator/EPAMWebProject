package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class ErrorCommand implements Command {

    private static final Command INSTANCE = new ErrorCommand();
    private static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext ERROR_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ERROR_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private ErrorCommand() {
    }

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {
        return ERROR_PAGE_CONTEXT;
    }
}
