package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.BankAccountService;
import com.epam.jwd.service.impl.payment_system.CreditCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteCreditCardCommand implements Command {

    private static final Command INSTANCE = new DeleteCreditCardCommand();
    private final Service<BankAccountDTO, Integer> bankAccountService = new BankAccountService();
    private final CreditCardService creditCardService = new CreditCardService();
    private static final String PAGE_PATH = "/bank?command=show_users_credit_cards_command";
    private static final String BANK_ACCOUNT_ID_ATTRIBUTE = "bankAccountId";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String ERROR_MESSAGE = "Can't delete bank account information ";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String MESSAGE = "User Account is successfully deleted ";
    private static final String FAIL_MESSAGE = "Account can't be deleted ";

    private final static Logger log = LogManager.getLogger(DeleteCreditCardCommand.class);

    private static final ResponseContext DELETE_CREDIT_CARD_CONTEXT = new ResponseContext() {
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

        Integer bankAccountId = Integer.valueOf(context.getParameterByName(BANK_ACCOUNT_ID_ATTRIBUTE));
        Integer userId = Integer.valueOf(context.getParameterByName(USER_ID_ATTRIBUTE));

        boolean isDone = false;
        try {
            BankAccountDTO bankAccount = bankAccountService.findById(bankAccountId);
            CreditCardDTO creditCard = creditCardService.findCreditCardByBankAccountId(bankAccount.getId());

            context.addAttributeToJsp(USER_ID_ATTRIBUTE, userId);
            creditCardService.delete(creditCard);
            bankAccountService.delete(bankAccount);

            isDone = true;
        } catch (ServiceException e) {
            e.printStackTrace();
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        if (isDone) {
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, MESSAGE);
        } else {
            context.addAttributeToJsp(ERROR_ATTRIBUTE, FAIL_MESSAGE);
        }

        return DELETE_CREDIT_CARD_CONTEXT;
    }
}
