package com.epam.jwd.controller.request_context;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * RequestContext interface which wrap request
 *
 * @see javax.servlet.http.HttpServletRequest
 */
public interface RequestContext {

    /**
     * Method which adds attribute to jsp
     *
     * @param attrName attribute name
     * @param attr     attribute value
     */
    void addAttributeToJsp(String attrName, Object attr);

    /**
     * Method for getting current session if it's present
     *
     * @return Optional of HttpSession
     */
    Optional<HttpSession> getCurrentSession();

    /**
     * Method for getting parameter from request by its name
     *
     * @param paramName parameter name
     * @return String parameter
     */
    String getParameterByName(String paramName);

    /**
     * Method for session invalidation
     */
    void invalidateCurrentSession();

    /**
     * Method for creating new session
     *
     * @return created session
     */
    HttpSession createSession();

    /**
     * Method for getting http headers
     *
     * @return http header as a string
     */
    String getHeader();

    /**
     * Method for getting ContextPath
     *
     * @return Context path as a string
     */
    String getContextPath();
}
