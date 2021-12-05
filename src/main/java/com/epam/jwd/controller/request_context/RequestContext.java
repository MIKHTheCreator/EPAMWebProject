package com.epam.jwd.controller.request_context;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface RequestContext {

    void addAttributeToJsp(String attrName, Object attr);

    Optional<HttpSession> getCurrentSession();

    String getParameterByName(String paramName);

    void invalidateCurrentSession();

    HttpSession createSession();

    String getHeader();

    String getContextPath();
}
