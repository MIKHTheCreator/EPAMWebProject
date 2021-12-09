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

public class DeblockUsersBankAccountCommand implements Command {

    private final Service<BankAccountDTO, Integer> bankAccountService = new BankAccountService();
    private static final Command INSTANCE = new DeblockUsersBankAccountCommand();
    private static final String PAGE_PATH = "/bank?command=show_users_credit_cards_command";
    private static final String BANK_ACCOUNT_ID_ATTRIBUTE = "bankAccountId";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String ERROR_MESSAGE = "Can't update bank account status ";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String MESSAGE = "User is successfully Deblocked ";
    private static final String ALREADY_DEBLOCKED_MESSAGE = "User is already Deblocked ";
    private static final String FAIL_MESSAGE = "Account can't be Deblocked ";

    private static final Logger log = LogManager.getLogger(DeblockUsersBankAccountCommand.class);

    private static final ResponseContext DEBLOCK_USERS_BANK_ACCOUNT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private DeblockUsersBankAccountCommand() {

    }

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
            if (!bankAccount.isBlocked()) {
                isDone = true;
                context.addAttributeToJsp(MESSAGE_ATTRIBUTE, ALREADY_DEBLOCKED_MESSAGE);
                context.addAttributeToJsp(USER_ID_ATTRIBUTE, userId);
                return DEBLOCK_USERS_BANK_ACCOUNT_CONTEXT;
            }

            context.addAttributeToJsp(USER_ID_ATTRIBUTE, userId);
            bankAccount.setBlocked(false);
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

        return DEBLOCK_USERS_BANK_ACCOUNT_CONTEXT;
    }
}
