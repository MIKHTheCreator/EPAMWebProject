package com.epam.jwd.controller.command.impl.show_page;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class ShowSaveUserPageCommand implements Command {

    private static final Command INSTANCE = new ShowSaveUserPageCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/create_account.jsp";

    private static final ResponseContext SHOW_SAVE_USER_PAGE_CONTEXT = new ResponseContext() {
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
        return SHOW_SAVE_USER_PAGE_CONTEXT;
    }
}
