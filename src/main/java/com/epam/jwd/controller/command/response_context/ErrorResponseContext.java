package com.epam.jwd.controller.command.response_context;

public class ErrorResponseContext implements ResponseContext {

    private static final String ERROR_PATH = "/WEB-INF/jsp/error.jsp";
    private static final ResponseContext INSTANCE = new ErrorResponseContext();

    private ErrorResponseContext() {

    }

    @Override
    public String getPage() {
        return ERROR_PATH;
    }

    @Override
    public boolean isRedirect() {
        return false;
    }

    public static ResponseContext getInstance() {
        return INSTANCE;
    }
}
