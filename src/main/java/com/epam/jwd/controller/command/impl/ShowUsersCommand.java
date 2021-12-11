package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.service.impl.user_account.UserService;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowUsersCommand implements Command {

    private final UserService userService = new UserService();
    private static final Command INSTANCE = new ShowUsersCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/users.jsp";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Can't find users ";
    private static final String PAGE_ATTRIBUTE = "page";
    private static final Integer TOTAL_USERS_ON_PAGE = 5;
    private static final Integer STARTER_PAGE = 1;

    private static final Logger log = LogManager.getLogger(ShowUsersCommand.class);

    private static final ResponseContext SHOW_USERS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext ERROR_CONTEXT = ErrorResponseContext.getInstance();

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        HttpSession session;

        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        int page;
        if(context.getParameterByName(PAGE_ATTRIBUTE) == null) {
            page = STARTER_PAGE;
        } else {
            page = Integer.parseInt(context.getParameterByName(PAGE_ATTRIBUTE));
        }

        if(page != 1) {
            page = page - 1;
            page = page * TOTAL_USERS_ON_PAGE + 1;
        }

        try {
            List<UserDTO> users = userService.findUsersToPage(page, TOTAL_USERS_ON_PAGE);
            session.setAttribute(USERS_ATTRIBUTE, users);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        return SHOW_USERS_CONTEXT;
    }
}
