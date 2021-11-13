package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.user_account.PassportDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.user_account.PassportService;
import com.epam.jwd.service.impl.user_account.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public class SavePassportCommand implements Command {

    private final Service<PassportDTO, Integer> passportService = new PassportService();
    private final Service<UserDTO, Integer> userService = new UserService();
    private static final Command INSTANCE = new SavePassportCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/passport_info.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String SERIA_AND_NUMBER_ATTRIBUTE = "seriaAndNumber";
    private static final String PERSONAL_NUMBER_ATTRIBUTE = "personalNumber";
    private static final String EXPIRATION_DATE_ATTRIBUTE = "expirationDate";
    private static final String PASSPORT_ATTRIBUTE = "passport";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String SUCCESSFUL_PASSPORT_CREATION = "Passport data was created";
    private static final String ERROR_MESSAGE = "Invalid passport data";

    private static final Logger log = LogManager.getLogger(SavePassportCommand.class);

    private static final ResponseContext SAVE_PASSPORT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
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

        HttpSession session = context.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);

        String seriaAndNumber = context.getParameterByName(SERIA_AND_NUMBER_ATTRIBUTE);
        String personalNumber = context.getParameterByName(PERSONAL_NUMBER_ATTRIBUTE);
        LocalDate expirationDate = LocalDate.parse(context.getParameterByName(EXPIRATION_DATE_ATTRIBUTE));

        try {
            PassportDTO passport = new PassportDTO(seriaAndNumber, personalNumber, expirationDate);
            passport = passportService.save(passport);
            user.setPassportId(passport.getId());
            userService.update(user);
            session.setAttribute(USER_ATTRIBUTE, user);
            context.addAttributeToJsp(PASSPORT_ATTRIBUTE, passport);
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, SUCCESSFUL_PASSPORT_CREATION);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE);
        }


        return SAVE_PASSPORT_CONTEXT;
    }
}
