package com.epam.jwd.controller.request_context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

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
    public Optional<HttpSession> getCurrentSession(boolean flag) {
        return Optional.ofNullable(this.request.getSession());
    }

    @Override
    public String getParameterByName(String paramName) {
        return this.request.getParameter(paramName);
    }

    @Override
    public void invalidateCurrentSession() {
        final HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }

    @Override
    public HttpSession createSession() {
        return request.getSession(true);
    }
}
