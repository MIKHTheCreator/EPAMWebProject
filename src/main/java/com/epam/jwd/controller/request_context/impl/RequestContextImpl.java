package com.epam.jwd.controller.request_context.impl;

import com.epam.jwd.controller.request_context.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class RequestContextImpl implements RequestContext {

    private final HttpServletRequest request;

    public RequestContextImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void addAttributeToJsp(String attrName, Object attr) {
        request.setAttribute(attrName, attr);
    }
}
