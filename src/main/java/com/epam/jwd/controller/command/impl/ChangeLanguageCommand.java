package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    private static final Command INSTANCE = new ChangeLanguageCommand();
    private static final String LANGUAGE_ATTRIBUTE = "language";
    private static String pagePath;

    private ChangeLanguageCommand() {
    }

    private static final ResponseContext CHANGE_LANGUAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return pagePath;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext ERROR_CONTEXT = ErrorResponseContext.getInstance();

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        String language = context.getParameterByName(LANGUAGE_ATTRIBUTE);

        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        session.setAttribute(LANGUAGE_ATTRIBUTE, language);
        pagePath = context.getContextPath() + context.getHeader();
        return CHANGE_LANGUAGE_CONTEXT;
    }
}
