package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.dao.entity.user_account.Gender;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.UserService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.user_account.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class SaveUserCommand implements Command {

    private final Service<UserDTO, Integer> userService = new UserService();
    private final Validator<UserDTO, Integer> validator = new UserValidator();
    private static final Command INSTANCE = new SaveUserCommand();
    private static final String FILL_USERDATA_PAGE = "/WEB-INF/jsp/create_account.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String FIRST_NAME_ATTRIBUTE = "firstName";
    private static final String SECOND_NAME_ATTRIBUTE = "secondName";
    private static final String PHONE_NUMBER_ATTRIBUTE = "phoneNumber";
    private static final String AGE_ATTRIBUTE = "age";
    private static final String GENDER_ATTRIBUTE = "gender";
    private static final String CLIENT_ATTRIBUTE = "currentClient";
    private static final String CURRENT_USER_ATTRIBUTE = "currentUser";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Saving data was interrupted";
    private static final String SUCCESSFUL_USER_CREATION_MESSAGE = "User was created";

    private static final Logger log = LogManager.getLogger(SaveUserCommand.class);

    private static final ResponseContext SAVE_USER_RESPONSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FILL_USERDATA_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return false;
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
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }
        ClientDTO clientDTO = (ClientDTO) session.getAttribute(CLIENT_ATTRIBUTE);
        String firstName = context.getParameterByName(FIRST_NAME_ATTRIBUTE);
        String secondName = context.getParameterByName(SECOND_NAME_ATTRIBUTE);
        String phoneNumber = context.getParameterByName(PHONE_NUMBER_ATTRIBUTE);
        Integer age = Integer.parseInt(context.getParameterByName(AGE_ATTRIBUTE));
        String gender = context.getParameterByName(GENDER_ATTRIBUTE);

        try {
            UserDTO user = new UserDTO.Builder()
                    .withFirstName(firstName)
                    .withSecondName(secondName)
                    .withPhoneNumber(phoneNumber)
                    .withAge(age)
                    .withGender(Gender.valueOf(gender.toUpperCase()))
                    .withClientId(clientDTO.getId())
                    .withRole(Role.USER)
                    .build();

            validator.validate(user);

            user = userService.save(user);
            context.addAttributeToJsp(CURRENT_USER_ATTRIBUTE, user);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, SUCCESSFUL_USER_CREATION_MESSAGE);
            session.setAttribute(CURRENT_USER_ATTRIBUTE, user);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        return SAVE_USER_RESPONSE_CONTEXT;
    }
}
