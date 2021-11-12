package com.epam.jwd.controller.request_context;

import javax.servlet.http.HttpSession;

public interface RequestContext {

    void addAttributeToJsp(String attrName, Object attr);

    HttpSession getSession();
}
