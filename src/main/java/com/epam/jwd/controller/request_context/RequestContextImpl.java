package com.epam.jwd.controller.request_context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Class which implements RequestContext interface and overrides methods
 */
public class RequestContextImpl implements RequestContext {


    private final HttpServletRequest request;

    private static final String REFERER = "referer";

    public RequestContextImpl(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @param attrName attribute name
     * @param attr     attribute value
     * @see RequestContext#addAttributeToJsp(String, Object)
     */
    @Override
    public void addAttributeToJsp(String attrName, Object attr) {
        request.setAttribute(attrName, attr);
    }

    /**
     * @see RequestContext#getCurrentSession()
     */
    @Override
    public Optional<HttpSession> getCurrentSession() {
        return Optional.ofNullable(this.request.getSession());
    }

    /**
     * @param paramName parameter name
     * @see RequestContext#getParameterByName(String)
     */
    @Override
    public String getParameterByName(String paramName) {
        return this.request.getParameter(paramName);
    }

    /**
     * @see RequestContext#invalidateCurrentSession()
     */
    @Override
    public void invalidateCurrentSession() {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * @see RequestContext#createSession()
     */
    @Override
    public HttpSession createSession() {
        return request.getSession(true);
    }

    /**
     * @see RequestContext#getHeader()
     */
    @Override
    public String getHeader() {
        return request.getHeader(REFERER);
    }

    /**
     * @see RequestContext#getContextPath()
     */
    @Override
    public String getContextPath() {
        return request.getContextPath();
    }
}
