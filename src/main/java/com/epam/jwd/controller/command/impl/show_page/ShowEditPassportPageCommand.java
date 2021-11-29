package com.epam.jwd.controller.command.impl.show_page;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class ShowEditPassportPageCommand implements Command {

    private static final Command INSTANCE = new ShowEditPassportPageCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/passport_creation.jsp";
    private static final ResponseContext SHOW_EDIT_PASSPORT_PAGE_CONTEXT = new ResponseContext() {
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
        return SHOW_EDIT_PASSPORT_PAGE_CONTEXT;
    }
}
