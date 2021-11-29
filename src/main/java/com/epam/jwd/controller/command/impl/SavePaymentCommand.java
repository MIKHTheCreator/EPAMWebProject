package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.payment_system.BankAccountDTO;
import com.epam.jwd.service.dto.payment_system.PaymentDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.BankAccountService;
import com.epam.jwd.service.impl.payment_system.PaymentService;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.payment_system.PaymentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SavePaymentCommand implements Command {

    private final Validator<PaymentDTO, Integer> validator = new PaymentValidator();
    private final PaymentService paymentService = new PaymentService();
    private final Service<BankAccountDTO, Integer> bankAccountService = new BankAccountService();
    private static final Command INSTANCE = new SavePaymentCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/payments.jsp";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/create_payment.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String SUM_ATTRIBUTE = "sum";
    private static final String DATE_ATTRIBUTE = "date";
    private static final String ORGANIZATION_ATTRIBUTE = "organization";
    private static final String GOAL_ATTRIBUTE = "goal";
    private static final String BANK_ACCOUNT_ID_ATTRIBUTE = "bankAccountId";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String MESSAGE = "Payment was successfully created";
    private static final String FAIL_MESSAGE = "Payment wasn't created";
    private static final String ERROR_MESSAGE = "Enable to create a payment";

    private static final Logger log = LogManager.getLogger(SavePaymentCommand.class);

    private static final ResponseContext SAVE_PAYMENT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
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

        BigDecimal sum = new BigDecimal(context.getParameterByName(SUM_ATTRIBUTE));
        LocalDate date = LocalDate.parse(context.getParameterByName(DATE_ATTRIBUTE));
        String organization = context.getParameterByName(ORGANIZATION_ATTRIBUTE);
        String goal = context.getParameterByName(GOAL_ATTRIBUTE);
        Integer bankAccountId = Integer.valueOf((context.getParameterByName(BANK_ACCOUNT_ID_ATTRIBUTE)));

        HttpSession session;
        if(context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_PAGE_CONTEXT;
        }

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);

        PaymentDTO payment = new PaymentDTO.Builder()
                .withSumOfPayment(sum)
                .withDateOfPayment(date)
                .withPaymentOrganization(organization)
                .withPaymentGoal(goal)
                .withBankAccountId(bankAccountId)
                .withUserId(user.getId())
                .build();

        boolean isDone = false;
        try {
            validator.validate(payment);
            paymentService.save(payment);
            BankAccountDTO bankAccount = bankAccountService.findById(bankAccountId);
            bankAccount.setBalance(bankAccount.getBalance().add(sum));
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
}
