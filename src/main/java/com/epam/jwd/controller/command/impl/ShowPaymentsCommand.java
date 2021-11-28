package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContext;
import com.epam.jwd.service.dto.payment_system.PaymentDTO;
import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.payment_system.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowPaymentsCommand implements Command {

    private final PaymentService paymentService = new PaymentService();
    private static final Command INSTANCE = new ShowPaymentsCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/payments.jsp";
    private static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String PAYMENTS_ATTRIBUTE = "payments";
    private static final String ERROR_ATTRIBUTE = "payments";
    private static final String ERROR_MESSAGE = "Can't find payments";

    private static final Logger log = LogManager.getLogger(ShowPaymentsCommand.class);

    private static final ResponseContext SHOW_PAYMENTS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ERROR_PAGE;
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

        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        UserDTO user = (UserDTO) session.getAttribute(USER_ATTRIBUTE);

        try {
            List<PaymentDTO> payments = paymentService.findPaymentsByUserId(user.getId());
            session.setAttribute(PAYMENTS_ATTRIBUTE, payments);
        } catch (ServiceException e) {
            log.error(ERROR_MESSAGE, e);
            context.addAttributeToJsp(ERROR_ATTRIBUTE, ERROR_MESSAGE + e.getMessage());
        }

        return SHOW_PAYMENTS_CONTEXT;
    }
}

