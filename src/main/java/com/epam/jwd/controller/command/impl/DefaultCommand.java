package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;

import javax.servlet.http.HttpSession;

public class DefaultCommand implements Command {

    private static final Command INSTANCE = new DefaultCommand();
    private static final String DEFAULT_PATH = "/WEB-INF/jsp/main.jsp";
    private static final String LANGUAGE_ATTRIBUTE = "language";
    private static final String ENGLISH_LANGUAGE_ATTRIBUTE = "en";

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
        HttpSession session = context.getCurrentSession().orElse(context.createSession());
        session.setAttribute(LANGUAGE_ATTRIBUTE, ENGLISH_LANGUAGE_ATTRIBUTE);
        return DEFAULT_PAGE_CONTEXT;
    }
}
