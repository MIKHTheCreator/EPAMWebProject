package com.epam.jwd.controller.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

/**
 * Personal  jsp tag
 */
public class GreetingTag extends TagSupport {

    private static final String USER_WELCOME_MESSAGE = "Welcome back, %s";
    private static final String DEFAULT_WELCOME_MESSAGE = "Welcome!";
    private static final String USER_NAME_SESSION_ATTRIB = "name";
    private static final String IOEXCEPTION_MESSAGE = "IOException caught";
    private static final Logger log = LogManager.getLogger(GreetingTag.class);

    @Override
    public int doStartTag() throws JspException {
        final String tagResultText = buildWelcomeMessage();
        printMessage(tagResultText);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    /**
     * Method for building welcome message for current user
     * @return greeting string
     */
    private String buildWelcomeMessage() {
        return Optional.ofNullable(pageContext.getSession())
                .map(session -> session.getAttribute(USER_NAME_SESSION_ATTRIB))
                .map(name -> String.format(USER_WELCOME_MESSAGE, name))
                .orElse(DEFAULT_WELCOME_MESSAGE);
    }

    /**
     * Method for printing generated greeting text
     * @param tagResultText greeting text
     */
    private void printMessage(String tagResultText) throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            out.write(tagResultText);
        } catch (IOException e) {
            log.error(IOEXCEPTION_MESSAGE, e);
            throw new JspException(e);
        }
    }
}
