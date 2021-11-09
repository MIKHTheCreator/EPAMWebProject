package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.UserService;

import java.util.List;

public class ShowUsersCommand implements Command {

    private static final Command INSTANCE = new ShowUsersCommand();
    private final Service<UserDTO, Integer> userService;
    private static final String SHOW_USER_PAGE ="/WEB-INF/jsp/users.jsp";
    private static final String USERS_JSP_ATTRIBUTE = "users";

    private static final ResponseContext SHOW_USERS_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return SHOW_USER_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private ShowUsersCommand() {
        this.userService = new UserService();
    }

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) throws ServiceException {
        List<UserDTO> users = userService.findAll();
        context.addAttributeToJsp(USERS_JSP_ATTRIBUTE, users);
        return SHOW_USERS_PAGE_CONTEXT;
    }
}
