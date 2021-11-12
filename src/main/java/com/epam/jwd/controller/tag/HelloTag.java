package com.epam.jwd.controller.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class HelloTag extends TagSupport {

    private String userName;

    private static final String LANGUAGE_ATTRIBUTE_NAME = "language";
    private static final String LOCALE_PATH = "resources/locale";
    private static final String HELLO_STRING_KEY = "helloString";
    private static final Logger log = LogManager.getLogger(HelloTag.class);
    private static final String TAG_ERROR_MESSAGE = "Tag outputting has been failed";

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int doStartTag() {

        Locale locale = new Locale((String) pageContext.getSession().getAttribute(LANGUAGE_ATTRIBUTE_NAME));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LOCALE_PATH, locale);

        String helloString = resourceBundle.getString(HELLO_STRING_KEY) + userName;

        try {
            pageContext.getOut().write("<h4><strong>" + helloString+ "</strong></h4>");
        } catch (IOException e) {
            log.error(TAG_ERROR_MESSAGE, e);
        }

        return SKIP_BODY;
    }
}
