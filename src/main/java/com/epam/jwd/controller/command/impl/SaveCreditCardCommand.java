package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.credit_card_config.CreditCardConfig;
import com.epam.jwd.service.credit_card_config.CreditCardConfigImpl;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.dto.payment_system.CreditCardDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.BankAccountService;
import com.epam.jwd.service.impl.payment_system.CreditCardService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.input_validator.InputValidator;
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
    private final Validator<CreditCardDTO, Integer> creditCardValidator = CreditCardValidator.getInstance();
    private final Validator<BankAccountDTO, Integer> bankAccountValidator = BankAccountValidator.getInstance();
    private final InputValidator inputValidator = InputValidator.getInstance();
    private final CreditCardConfig creditCardConfig = CreditCardConfigImpl.getInstance();
    private static final Command INSTANCE = new SaveCreditCardCommand();
    private static final String PAGE_PATH = "/bank?command=show_credit_cards_command";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/create_credit_card.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String CURRENCY_ATTRIBUTE = "currency";
    private static final String CREDIT_CARD_NUMBER_ATTRIBUTE = "creditCardNumber";
    private static final String EXPIRATION_MONTH_ATTRIBUTE = "expirationMonth";
    private static final String EXPIRATION_YEAR_ATTRIBUTE = "expirationYear";
    private static final String FULL_NAME_ATTRIBUTE = "fullName";
    private static final String BANK_ACCOUNT_ERROR = "Can't create bank account with such parameters ";
    private static final String CREDIT_CARD_ERROR = "Can't create credit card with such parameters ";
    private static final String DEFAULT_MONTH = "01";
    private static final String DEFAULT_YEAR = "2021";
    private static final BigDecimal STARTER_BALANCE = new BigDecimal(0);

    private static final Logger log = LogManager.getLogger(SaveCreditCardCommand.class);

    private SaveCreditCardCommand() {
    }

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

    private static final ResponseContext FAIL_TO_SAVE_CREDIT_CARD_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
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
        HttpSession session;

        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_PAGE_CONTEXT;
        }

        String currency = context.getParameterByName(CURRENCY_ATTRIBUTE);
        String creditCardNumber = context.getParameterByName(CREDIT_CARD_NUMBER_ATTRIBUTE);
        String expirationMonth;
        if (!inputValidator.isValidNumberFormat(context.getParameterByName(EXPIRATION_MONTH_ATTRIBUTE))) {
            expirationMonth = DEFAULT_MONTH;
        } else {
            expirationMonth = context.getParameterByName(EXPIRATION_MONTH_ATTRIBUTE);
        }
        String expirationYear;
        if (!inputValidator.isValidNumberFormat(context.getParameterByName(EXPIRATION_YEAR_ATTRIBUTE))) {
            expirationYear = DEFAULT_YEAR;
        } else {
            expirationYear = context.getParameterByName(EXPIRATION_YEAR_ATTRIBUTE);
        }
        LocalDate expirationDate = LocalDate.parse(expirationYear + "-" + expirationMonth + "-01");
        String fullName = context.getParameterByName(FULL_NAME_ATTRIBUTE);

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);

        BankAccountDTO bankAccount = new BankAccountDTO(STARTER_BALANCE, currency, false);

        try {
            bankAccountValidator.validate(bankAccount);
            bankAccount = bankAccountService.save(bankAccount);
            CreditCardDTO creditCard = createCreditCard(creditCardNumber, expirationDate, fullName, bankAccount, user);
            creditCardValidator.validate(creditCard);
            creditCardService.save(creditCard);
        } catch (ServiceException e) {
            log.error(CREDIT_CARD_ERROR, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, CREDIT_CARD_ERROR + e.getMessage());
            try {
                bankAccountService.delete(bankAccount);
            } catch (ServiceException ex) {
                log.error(BANK_ACCOUNT_ERROR + ex.getMessage());
                context.addAttributeToJsp(ERROR_ATTRIBUTE, BANK_ACCOUNT_ERROR);
                return FAIL_TO_SAVE_CREDIT_CARD_CONTEXT;
            }
            return FAIL_TO_SAVE_CREDIT_CARD_CONTEXT;
        }

        return SAVE_CREDIT_CARD_CONTEXT;
    }

    private CreditCardDTO createCreditCard(String creditCardNumber, LocalDate expirationDate,
                                           String fullName, BankAccountDTO bankAccount, UserDTO user) {
        return new CreditCardDTO.Builder()
                .withNumber(creditCardNumber)
                .withExpirationDate(expirationDate)
                .withCVV(creditCardConfig.generateCVVCode())
                .withFullName(fullName)
                .withBankAccount(bankAccount)
                .withUserId(user.getId())
                .withPin(creditCardConfig.generatePinCode())
                .build();
    }
}
