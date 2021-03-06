package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.dao.entity.user_account.Gender;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.service.dto.user_account.ClientDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.UserService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.input_validator.InputValidator;
import com.epam.jwd.service.validator.user_account.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class SaveUserCommand implements Command {

    private final InputValidator inputValidator = InputValidator.getInstance();
    private final UserService userService = new UserService();
    private final Validator<UserDTO, Integer> validator = UserValidator.getInstance();
    private static final Command INSTANCE = new SaveUserCommand();
    private static final String FILL_USERDATA_PAGE = "/bank?command=show_user_info_page_command";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/create_account.jsp";
    private static final String FIRST_NAME_ATTRIBUTE = "firstName";
    private static final String SECOND_NAME_ATTRIBUTE = "secondName";
    private static final String PHONE_NUMBER_ATTRIBUTE = "phoneNumber";
    private static final String AGE_ATTRIBUTE = "age";
    private static final String GENDER_ATTRIBUTE = "gender";
    private static final String CLIENT_ATTRIBUTE = "currentClient";
    private static final String CURRENT_USER_ATTRIBUTE = "currentUser";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Saving data was interrupted ";
    private static final String SUCCESSFUL_USER_CREATION_MESSAGE = "User was created ";

    private static final Logger log = LogManager.getLogger(SaveUserCommand.class);

    private static final ResponseContext SUCCESSFUL_SAVE_USER_RESPONSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FILL_USERDATA_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext SAVE_USER_RESPONSE_FAIL_CONTEXT = new ResponseContext() {
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
        ClientDTO clientDTO = (ClientDTO) session.getAttribute(CLIENT_ATTRIBUTE);
        String firstName = context.getParameterByName(FIRST_NAME_ATTRIBUTE);
        String secondName = context.getParameterByName(SECOND_NAME_ATTRIBUTE);
        String phoneNumber = context.getParameterByName(PHONE_NUMBER_ATTRIBUTE);
        String ageString = context.getParameterByName(AGE_ATTRIBUTE);
        int age;
        if(inputValidator.isValidAgeFormat(ageString)) {
            age = Integer.parseInt(ageString);
        } else {
            return provideWithFailContext(context);
        }
        String gender = context.getParameterByName(GENDER_ATTRIBUTE);

        try {
            UserDTO user = createUser(firstName, secondName, phoneNumber, age, gender, clientDTO);

            validator.validate(user);

            user = userService.save(user);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, SUCCESSFUL_USER_CREATION_MESSAGE);
            session.setAttribute(CURRENT_USER_ATTRIBUTE, user);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            return provideWithFailContext(context);
        }


        return SUCCESSFUL_SAVE_USER_RESPONSE_CONTEXT;
    }

    private UserDTO createUser(String firstName, String secondName, String phoneNumber,
                               Integer age, String gender, ClientDTO clientDTO) {
        return new UserDTO.Builder()
                .withFirstName(firstName)
                .withSecondName(secondName)
                .withPhoneNumber(phoneNumber)
                .withAge(age)
                .withGender(Gender.valueOf(gender.toUpperCase()))
                .withClientId(clientDTO.getId())
                .withRole(Role.USER)
                .build();
    }

    private ResponseContext provideWithFailContext(RequestContext context) {
        context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE);
        return SAVE_USER_RESPONSE_FAIL_CONTEXT;
    }
}
