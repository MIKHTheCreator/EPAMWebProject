package com.epam.jwd.controller.command.impl.show_page;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class ShowRegistrationPageCommand implements Command {

    private static final Command INSTANCE = new ShowRegistrationPageCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/registration.jsp";
    private static final ResponseContext SHOW_REGISTRATION_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {
        return SHOW_REGISTRATION_PAGE_CONTEXT;
    }
}
