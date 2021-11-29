package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockUsersBankAccountCommand implements Command {

    private final Service<BankAccountDTO, Integer> bankAccountService = new BankAccountService();
    private static final Command INSTANCE = new BlockUsersBankAccountCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/credit_cards.jsp";
    private static final String BANK_ACCOUNT_ID_ATTRIBUTE = "bankAccountId";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE = "Can't update bank account status";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String MESSAGE = "User is successfully blocked";
    private static final String FAIL_MESSAGE = "Account can't be blocked";

    private BlockUsersBankAccountCommand() {
    }

    private static final Logger log = LogManager.getLogger(BlockUsersBankAccountCommand.class);

    private static final ResponseContext BLOCK_USERS_CREDIT_CARD_CONTEXT = new ResponseContext() {
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

        boolean isDone = false;
        try {
            BankAccountDTO bankAccount = bankAccountService.findById(bankAccountId);
            bankAccount.setBlocked(true);
            bankAccountService.update(bankAccount);
            isDone = true;
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        if (isDone) {
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, MESSAGE);
        } else {
            context.addAttributeToJsp(ERROR_ATTRIBUTE, FAIL_MESSAGE);
        }

        return BLOCK_USERS_CREDIT_CARD_CONTEXT;
    }
}
