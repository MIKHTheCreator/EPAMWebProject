package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

import javax.servlet.http.HttpSession;

public class DefaultCommand implements Command {

    private static final Command INSTANCE = new DefaultCommand();
    private static final String DEFAULT_PATH = "/WEB-INF/jsp/main.jsp";
    private static final String LANGUAGE_ATTRIBUTE = "language";
    private static final String ENGLISH_LANGUAGE_ATTRIBUTE = "en";

    private static final ResponseContext ERROR_CONTEXT = ErrorResponseContext.getInstance();
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

        HttpSession session;

        if(context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }


        String language = (String) session.getAttribute(LANGUAGE_ATTRIBUTE);

        context.invalidateCurrentSession();
        session = context.createSession();
        session.setAttribute(LANGUAGE_ATTRIBUTE, language);
        return DEFAULT_PAGE_CONTEXT;
    }
}
