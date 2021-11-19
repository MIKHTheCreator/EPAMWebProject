package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.BankAccountService;
import com.epam.jwd.service.impl.payment_system.CreditCardService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.payment_system.BankAccountValidator;
import com.epam.jwd.service.validator.payment_system.CreditCardValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SaveCreditCardCommand implements Command {

    private final CreditCardService creditCardService = new CreditCardService();
    private final Service<BankAccountDTO, Integer> bankAccountService = new BankAccountService();
    private final Validator<CreditCardDTO, Integer> creditCardValidator = new CreditCardValidator();
    private final  Validator<BankAccountDTO, Integer> bankAccountValidator = new BankAccountValidator();
    private static final Command INSTANCE = new SaveCreditCardCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/credit_cards.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String CURRENCY_ATTRIBUTE = "currency";
    private static final String CREDIT_CARD_NUMBER_ATTRIBUTE = "creditCardNumber";
    private static final String EXPIRATION_DATE_ATTRIBUTE = "expirationDate";
    private static final String FULL_NAME_ATTRIBUTE = "fullName";
    private static final String CVV_ATTRIBUTE = "cvv";
    private static final String PIN_ATTRIBUTE = "pin";
    private static final String BANK_ACCOUNT_ERROR = "Can't create bank account with such parameters";
    private static final String CREDIT_CARD_ERROR = "Can't create credit card with such parameters";
    private static final BigDecimal STARTER_BALANCE = new BigDecimal(0);

    private static final Logger log = LogManager.getLogger(SaveCreditCardCommand.class);

    private static final ResponseContext SAVE_CREDIT_CARD_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext ERROR_PAGE_CONTEXT = new ResponseContext() {
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

        if(context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_PAGE_CONTEXT;
        }

        String currency = context.getParameterByName(CURRENCY_ATTRIBUTE);
        String creditCardNumber = context.getParameterByName(CREDIT_CARD_NUMBER_ATTRIBUTE);
        LocalDate expirationDate = LocalDate.parse(context.getParameterByName(EXPIRATION_DATE_ATTRIBUTE));
        String fullName = context.getParameterByName(FULL_NAME_ATTRIBUTE);
        String cvv = context.getParameterByName(CVV_ATTRIBUTE);
        String pin = context.getParameterByName(PIN_ATTRIBUTE);

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);


        BankAccountDTO bankAccount = new BankAccountDTO(STARTER_BALANCE, currency, false);

        try {
            bankAccountValidator.validate(bankAccount);
            bankAccount = bankAccountService.save(bankAccount);
        } catch (ServiceException e) {
            log.error(BANK_ACCOUNT_ERROR, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, BANK_ACCOUNT_ERROR + e.getMessage());
        }

        CreditCardDTO creditCard = new CreditCardDTO.Builder()
                .withNumber(creditCardNumber)
                .withExpirationDate(expirationDate)
                .withCVV(cvv)
                .withFullName(fullName)
                .withBankAccountId(bankAccount.getId())
                .withUserId(user.getId())
                .withPin(pin)
                .build();

        try {
            creditCardValidator.validate(creditCard);
            creditCardService.save(creditCard);
        } catch (ServiceException e) {
            log.error(CREDIT_CARD_ERROR, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, CREDIT_CARD_ERROR + e.getMessage());
        }

        return SAVE_CREDIT_CARD_CONTEXT;
    }
}
