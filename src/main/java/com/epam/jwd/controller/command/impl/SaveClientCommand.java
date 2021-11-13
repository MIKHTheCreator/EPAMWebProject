package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class SaveClientCommand implements Command {

    private final Service<ClientDTO, Integer> clientService = new ClientService();
    private static final Command INSTANCE = new SaveClientCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/registration.jsp";
    private static final String PASSWORD_PARAM = "password";
    private static final String EMAIL_PARAM = "email";
    private static final String USERNAME_PARAM = "username";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String CURRENT_CLIENT_ATTRIBUTE_NAME = "currentClient";
    private static final String REGISTRATION_FAILED = "Registration failed";
    private static final String REGISTRATION_COMPLETED_MESSAGE = "Registration is successfully completed";
    private static final String ERROR_MESSAGE = "User with such email is already exists";

    private static final Logger log = LogManager.getLogger(SaveClientCommand.class);

    private final ResponseContext SAVE_CLIENT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private SaveClientCommand() {
    }

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        String username = context.getParameterByName(USERNAME_PARAM);
        String email = context.getParameterByName(EMAIL_PARAM);
        String password = context.getParameterByName(PASSWORD_PARAM);

        boolean isRegistrationSuccessful = false;
        Integer clientId = null;
        try {
            ClientDTO client = new ClientDTO(username, email, password);
            clientId = clientService.save(client).getId();
            isRegistrationSuccessful = true;
        } catch (ServiceException e) {
            log.error(REGISTRATION_FAILED, e);
        }

        HttpSession session = context.getSession();

        if(isRegistrationSuccessful) {
            ClientDTO client = new ClientDTO();
            client.setUsername(username);
            client.setEmail(email);
            client.setPassword(password);
            client.setId(clientId);
            session.setAttribute(CURRENT_CLIENT_ATTRIBUTE_NAME, client);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, REGISTRATION_COMPLETED_MESSAGE);
        } else {
            context.addAttributeToJsp(ERROR_ATTRIBUTE,  ERROR_MESSAGE);
        }

        return SAVE_CLIENT_CONTEXT;
    }
}
