package com.epam.jwd.controller.command.impl.show_page;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

public class ShowMakePaymentCommand implements Command {

    private static final Command INSTANCE = new ShowMakePaymentCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/create_payment.jsp";

    private ShowMakePaymentCommand() {
    }

    private static final ResponseContext SHOW_MAKE_PAYMENT_CONTEXT = new ResponseContext() {
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
        return SHOW_MAKE_PAYMENT_CONTEXT;
    }
}
