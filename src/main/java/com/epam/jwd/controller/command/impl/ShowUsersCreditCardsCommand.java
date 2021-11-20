package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.CreditCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowUsersCreditCardsCommand implements Command {

    private final CreditCardService creditCardService = new CreditCardService();
    private static final Command INSTANCE = new ShowUsersCreditCardsCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/credit_cards.jsp";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Can't find any credit cards";
    private static final String CREDIT_CARDS_ATTRIBUTE = "creditCards";

    private static final Logger log = LogManager.getLogger(ShowUsersCreditCardsCommand.class);

    private static final ResponseContext SHOW_USERS_CREDIT_CARDS_CONTEXT = new ResponseContext() {
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

        try {
            final List<CreditCardDTO> creditCards = creditCardService.findAll();
            context.addAttributeToJsp(CREDIT_CARDS_ATTRIBUTE, creditCards);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        return SHOW_USERS_CREDIT_CARDS_CONTEXT;
    }
}
