package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class ShowUsersCommand implements Command {

    private static final Command INSTANCE = new ShowUsersCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/users.jsp";

    private static final ResponseContext SHOW_USERS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
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
        return SHOW_USERS_CONTEXT;
    }
}
