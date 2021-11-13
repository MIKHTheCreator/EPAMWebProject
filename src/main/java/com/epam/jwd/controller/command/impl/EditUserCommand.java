package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class EditUserCommand implements Command {

    private final Service<UserDTO, Integer> userService = new UserService();
    private static final Command INSTANCE = new EditUserCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/edit_user.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String FIRST_NAME_ATTRIBUTE = "firstName";
    private static final String SECOND_NAME_ATTRIBUTE = "secondName";
    private static final String PHONE_NUMBER_ATTRIBUTE = "phoneNumber";
    private static final String AGE_ATTRIBUTE = "age";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Updating failed";
    private static final String SUCCESSFUL_USER_UPDATE = "Data has been updated";

    private static final Logger log = LogManager.getLogger(EditUserCommand.class);

    private static final ResponseContext EDIT_USER_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
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

        HttpSession session = context.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);
        String firstName = context.getParameterByName(FIRST_NAME_ATTRIBUTE);
        String secondName = context.getParameterByName(SECOND_NAME_ATTRIBUTE);
        String phoneNumber = context.getParameterByName(PHONE_NUMBER_ATTRIBUTE);
        Integer age = Integer.parseInt(context.getParameterByName(AGE_ATTRIBUTE));

        try {
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setPhoneNumber(phoneNumber);
            user.setAge(age);

            user = userService.update(user);
            context.addAttributeToJsp(USER_ATTRIBUTE, user);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, SUCCESSFUL_USER_UPDATE);
            session.setAttribute(USER_ATTRIBUTE, user);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE);
        }

        return EDIT_USER_CONTEXT;
    }
}
