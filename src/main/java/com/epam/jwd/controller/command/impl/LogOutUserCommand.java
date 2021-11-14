package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.dto.user_account.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class LogOutUserCommand implements Command {

    private static final Command INSTANCE = new LogOutUserCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/main.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String DEBUG_MESSAGE = "User with name: %s %s logged out of the service";

    private static final Logger log = LogManager.getLogger(LogOutUserCommand.class);

    private static final ResponseContext LOG_OUT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ERROR_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        HttpSession session;
        if(context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);
        log.debug(DEBUG_MESSAGE, user.getFirstName(), user.getSecondName());
        context.invalidateCurrentSession();
        return LOG_OUT_CONTEXT;
    }
}
