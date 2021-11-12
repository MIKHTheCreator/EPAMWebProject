package com.epam.jwd.controller.request_context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestContextImpl implements RequestContext {

    private final HttpServletRequest request;

    public RequestContextImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void addAttributeToJsp(String attrName, Object attr) {
        request.setAttribute(attrName, attr);
    }

    @Override
    public HttpSession getSession() {
        return this.request.getSession();
    }

    @Override
    public String getParameterByName(String paramName) {
        return this.request.getParameter(paramName);
    }
}
