package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class DefaultCommand implements Command {

    private static final Command INSTANCE = new DefaultCommand();
    private static final String DEFAULT_PATH = "/WEB-INF/jsp/main.jsp";

    private static final ResponseContext DEFAULT_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return DEFAULT_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private DefaultCommand() {
    }

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {
        return DEFAULT_PAGE_CONTEXT;
    }
}
