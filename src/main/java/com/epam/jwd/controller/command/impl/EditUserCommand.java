package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.dao.entity.user_account.Gender;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.UserService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.input_validator.InputValidator;
import com.epam.jwd.service.validator.user_account.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class EditUserCommand implements Command {

    private final InputValidator inputValidator = InputValidator.getInstance();
    private final UserService userService = new UserService();
    private final Validator<UserDTO, Integer> validator = UserValidator.getInstance();
    private static final Command INSTANCE = new EditUserCommand();
    private static final String PAGE_PATH = "/bank?command=show_user_info_page_command";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/edit_user_info.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String FIRST_NAME_ATTRIBUTE = "firstName";
    private static final String SECOND_NAME_ATTRIBUTE = "secondName";
    private static final String PHONE_NUMBER_ATTRIBUTE = "phoneNumber";
    private static final String AGE_ATTRIBUTE = "age";
    private static final String GENDER_ATTRIBUTE = "gender";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USERNAME_ATTRIBUTE = "name";
    private static final String ERROR_MESSAGE = "Updating failed ";
    private static final String SUCCESSFUL_USER_UPDATE = "Data has been updated ";

    private static final Logger log = LogManager.getLogger(EditUserCommand.class);

    private EditUserCommand() {
    }

    private static final ResponseContext SUCCESS_EDIT_USER_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext FAIL_EDIT_USER_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
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

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);
        String firstName = context.getParameterByName(FIRST_NAME_ATTRIBUTE);
        String secondName = context.getParameterByName(SECOND_NAME_ATTRIBUTE);
        String phoneNumber = context.getParameterByName(PHONE_NUMBER_ATTRIBUTE);
        String stringAge = context.getParameterByName(AGE_ATTRIBUTE);
        String gender = context.getParameterByName(GENDER_ATTRIBUTE);
        int age;
        if (inputValidator.isValidAgeFormat(stringAge)) {
            age = Integer.parseInt(stringAge);
        } else {
            return provideWithFailContext(context);
        }

        try {

            fillUserForm(firstName, user, secondName, phoneNumber, gender, age);

            validator.validate(user);

            user = userService.update(user);
            context.addAttributeToJsp(USER_ATTRIBUTE, user);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, SUCCESSFUL_USER_UPDATE);
            session.setAttribute(USER_ATTRIBUTE, user);
            session.setAttribute(USERNAME_ATTRIBUTE, user.getFirstName() + "\s" + user.getSecondName());
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
            return FAIL_EDIT_USER_CONTEXT;
        }

        return SUCCESS_EDIT_USER_CONTEXT;
    }

    private void fillUserForm(String firstName, UserDTO user, String secondName,
                              String phoneNumber, String gender, int age) {

        if (inputValidator.isEmptyString(firstName)) {
            user.setFirstName(user.getFirstName());
        } else {
            user.setFirstName(firstName);
        }

        if (inputValidator.isEmptyString(secondName)) {
            user.setSecondName(user.getSecondName());
        } else {
            user.setSecondName(secondName);
        }

        if (inputValidator.isEmptyString(phoneNumber)) {
            user.setPhoneNumber(user.getPhoneNumber());
        } else {
            user.setPhoneNumber(phoneNumber);
        }

        if (inputValidator.isZeroField(age)) {
            user.setAge(user.getAge());
        } else {
            user.setAge(age);
        }

        if (inputValidator.isEmptyString(gender)) {
            user.setRole(user.getRole());
        } else {
            user.setGender(Gender.valueOf(gender));
        }
    }

    private ResponseContext provideWithFailContext(RequestContext context) {
        context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE );
        return FAIL_EDIT_USER_CONTEXT;
    }
}
