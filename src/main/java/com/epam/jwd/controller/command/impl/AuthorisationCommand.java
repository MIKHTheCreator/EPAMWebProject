package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class AuthorisationCommand implements Command {

    private static final Command INSTANCE = new AuthorisationCommand();
    private static final String PAGE_PATH = "WEB-INF/jsp/account.jsp";
    private static final String ERROR_PATH = "WEB-INF/jsp/error.jsp";

    private static final ResponseContext AUTHORISATION_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ERROR_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {
        return ERROR_CONTEXT;
    }
}
