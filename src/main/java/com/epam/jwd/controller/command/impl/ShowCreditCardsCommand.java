package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.CreditCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowCreditCardsCommand implements Command {

    private final CreditCardService creditCardService = new CreditCardService();
    private static final Command INSTANCE = new ShowCreditCardsCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/credit_cards.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String CREDIT_CARDS_ATTRIBUTE = "creditCards";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Can't find user credit cards ";

    private static final Logger log = LogManager.getLogger(ShowCreditCardsCommand.class);

    private static final ResponseContext SHOW_CREDIT_CARDS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext ERROR_PAGE_CONTEXT = ErrorResponseContext.getInstance();

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        List<CreditCardDTO> creditCards = new ArrayList<>();

        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_PAGE_CONTEXT;
        }

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);

        try {
            creditCards = creditCardService.findCreditCardsByUserId(user.getId());
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE);
        }

        session.setAttribute(CREDIT_CARDS_ATTRIBUTE, creditCards);

        return SHOW_CREDIT_CARDS_CONTEXT;
    }
}
