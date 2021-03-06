package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.user_account.PassportDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.PassportService;
import com.epam.jwd.service.impl.user_account.UserService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.input_validator.InputValidator;
import com.epam.jwd.service.validator.user_account.PassportValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public class SavePassportCommand implements Command {

    private final Service<PassportDTO, Integer> passportService = new PassportService();
    private final UserService userService = new UserService();
    private final InputValidator inputValidator = InputValidator.getInstance();
    private final Validator<PassportDTO, Integer> validator = PassportValidator.getInstance();
    private static final Command INSTANCE = new SavePassportCommand();
    private static final String PAGE_PATH = "/bank?command=show_user_info_page_command";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/passport_creation.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String SERIA_AND_NUMBER_ATTRIBUTE = "seriaAndNumber";
    private static final String PERSONAL_NUMBER_ATTRIBUTE = "personalNumber";
    private static final String EXPIRATION_DATE_ATTRIBUTE = "expirationDate";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String SUCCESSFUL_PASSPORT_CREATION = "Passport data was created ";
    private static final String ERROR_MESSAGE = "Invalid passport data ";
    private static final LocalDate DEFAULT_DATE = LocalDate.parse("2022-04-04");

    private static final Logger log = LogManager.getLogger(SavePassportCommand.class);

    private static final ResponseContext SUCCESS_SAVE_PASSPORT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext FAIL_PASSPORT_CONTEXT = new ResponseContext() {
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

        String seriaAndNumber = context.getParameterByName(SERIA_AND_NUMBER_ATTRIBUTE);
        String personalNumber = context.getParameterByName(PERSONAL_NUMBER_ATTRIBUTE);
        LocalDate expirationDate;
        if (!inputValidator.isValidDateFormat(context.getParameterByName(EXPIRATION_DATE_ATTRIBUTE))) {
            expirationDate = DEFAULT_DATE;
        } else {
            expirationDate = LocalDate.parse(context.getParameterByName(EXPIRATION_DATE_ATTRIBUTE));
        }


        try {
            PassportDTO passport = new PassportDTO(seriaAndNumber, personalNumber, expirationDate);

            validator.validate(passport);

            passport = passportService.save(passport);
            user.setPassportId(passport.getId());

            userService.updateUserPassport(user);
            session.setAttribute(USER_ATTRIBUTE, user);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, SUCCESSFUL_PASSPORT_CREATION);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
            return FAIL_PASSPORT_CONTEXT;
        }

        return SUCCESS_SAVE_PASSPORT_CONTEXT;
    }
}
