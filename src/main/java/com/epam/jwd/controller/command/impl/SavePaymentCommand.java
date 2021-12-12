package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ErrorResponseContext;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.currency_converter.CurrencyConverter;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.dto.payment_system.PaymentDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.BankAccountService;
import com.epam.jwd.service.impl.payment_system.PaymentService;
import com.epam.jwd.service.payment_manager.PaymentManager;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.input_validator.InputValidator;
import com.epam.jwd.service.validator.payment_system.PaymentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SavePaymentCommand implements Command {

    private final CurrencyConverter converter = CurrencyConverter.getInstance();
    private final PaymentManager manager = PaymentManager.getInstance();
    private final Validator<PaymentDTO, Integer> validator = PaymentValidator.getInstance();
    private final InputValidator inputValidator = InputValidator.getInstance();
    private final PaymentService paymentService = new PaymentService();
    private final Service<BankAccountDTO, Integer> bankAccountService = new BankAccountService();
    private static final Command INSTANCE = new SavePaymentCommand();
    private static final String PAGE_PATH = "/bank?command=show_payments_command";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/create_payment.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String SUM_ATTRIBUTE = "sum";
    private static final String DATE_ATTRIBUTE = "date";
    private static final String ORGANIZATION_ATTRIBUTE = "organization";
    private static final String GOAL_ATTRIBUTE = "goal";
    private static final String CURRENCY_ATTRIBUTE = "currency";
    private static final String BANK_ACCOUNT_ID_ATTRIBUTE = "bankAccountId";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String MESSAGE = "Payment was successfully created ";
    private static final String FAIL_MESSAGE = "Payment wasn't created ";
    private static final String ERROR_MESSAGE = "Enable to create a payment ";
    private static final Integer SUBTRACT_OPERATION_NUMBER = 1;
    private static final String ZERO = "0";

    private static final Logger log = LogManager.getLogger(SavePaymentCommand.class);

    private static final ResponseContext SAVE_PAYMENT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final ResponseContext SAVE_FAIL_PAYMENT_CONTEXT = new ResponseContext() {
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

        BigDecimal sum;
        if (inputValidator.isEmptyString(context.getParameterByName(SUM_ATTRIBUTE))) {
            sum = new BigDecimal(ZERO);
        } else {
            sum = new BigDecimal(context.getParameterByName(SUM_ATTRIBUTE));
        }
        LocalDate date = LocalDate.parse(context.getParameterByName(DATE_ATTRIBUTE));
        String organization = context.getParameterByName(ORGANIZATION_ATTRIBUTE);
        String goal = context.getParameterByName(GOAL_ATTRIBUTE);
        String currency = context.getParameterByName(CURRENCY_ATTRIBUTE);
        Integer bankAccountId = Integer.valueOf((context.getParameterByName(BANK_ACCOUNT_ID_ATTRIBUTE)));

        HttpSession session;
        if(context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_PAGE_CONTEXT;
        }

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);

        boolean isDone = false;
        try {
            BankAccountDTO bankAccount = bankAccountService.findById(bankAccountId);
            sum = converter.convert(String.valueOf(sum), currency, bankAccount.getCurrency());

            PaymentDTO payment = createPayment(sum, date, organization, goal, bankAccountId, user);
            validator.validate(payment);
            BigDecimal bankAccountBalance = bankAccount.getBalance();
            if (manager.chooseOperation(goal, sum, bankAccountBalance).equals(SUBTRACT_OPERATION_NUMBER)) {
                bankAccount.setBalance(bankAccount.getBalance().subtract(sum));
            } else {
                bankAccount.setBalance(bankAccount.getBalance().add(sum));
            }
            paymentService.save(payment);
            bankAccountService.update(bankAccount);

            isDone = true;
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        if(isDone) {
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, MESSAGE);
        } else {
            context.addAttributeToJsp(ERROR_ATTRIBUTE, FAIL_MESSAGE);
            return SAVE_FAIL_PAYMENT_CONTEXT;
        }

        return SAVE_PAYMENT_CONTEXT;
    }

    private PaymentDTO createPayment(BigDecimal sum, LocalDate date, String organization,
                                     String goal, Integer bankAccountId, UserDTO user) {
        return new PaymentDTO.Builder()
                .withSumOfPayment(sum)
                .withDateOfPayment(date)
                .withPaymentOrganization(organization)
                .withPaymentGoal(goal)
                .withBankAccountId(bankAccountId)
                .withUserId(user.getId())
                .build();
    }
}
