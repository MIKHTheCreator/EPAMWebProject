package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.ClientService;
import com.epam.jwd.service.impl.user_account.UserService;
import com.epam.jwd.service.password_manager.PasswordManagerImpl;
import com.epam.jwd.service.password_manager.api.PasswordManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class AuthorisationCommand implements Command {

    private final PasswordManager passwordManager = new PasswordManagerImpl();
    private final UserService userService = new UserService();
    private final ClientService clientService = new ClientService();
    private static final Command INSTANCE = new AuthorisationCommand();
    private static final String PAGE_PATH = "WEB-INF/jsp/user_info.jsp";
    private static final String FAIL_PAGE_PATH = "WEB-INF/jsp/login.jsp";
    private static final String ERROR_PATH = "WEB-INF/jsp/error.jsp";
    private static final String USERNAME_ATTRIBUTE = "username";
    private static final String PASSWORD_ATTRIBUTE = "password";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String CURRENT_USER_ATTRIBUTE = "currentUser";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String MESSAGE = "There is no user with such username or password is incorrect";
    private static final String ERROR_MESSAGE = "Authorization failed";

    private static final Logger log = LogManager.getLogger(AuthorisationCommand.class);

    private static final ResponseContext SUCCESSFUL_AUTHORISATION_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext FAIL_AUTHORIZATION_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ERROR_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
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

        String password = context.getParameterByName(PASSWORD_ATTRIBUTE);
        String username = context.getParameterByName(USERNAME_ATTRIBUTE);

        try {
            ClientDTO client = clientService.findClientByUsername(username);
            if(client != null && passwordManager.checkForIdentity(password, client.getPassword())) {
                UserDTO user = userService.findUserByClientId(client.getId());
                session.setAttribute(CURRENT_USER_ATTRIBUTE, user);
            } else {
                context.addAttributeToJsp(MESSAGE_ATTRIBUTE, MESSAGE);
                return FAIL_AUTHORIZATION_CONTEXT;
            }

        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        return SUCCESSFUL_AUTHORISATION_CONTEXT;
    }
}
