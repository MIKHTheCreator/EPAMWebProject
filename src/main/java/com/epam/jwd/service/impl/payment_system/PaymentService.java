package com.epam.jwd.service.impl.payment_system;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.api.PaymentDAO;
import com.epam.jwd.dao.entity.payment_system.Payment;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.PaymentDAOImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.mapper.payment_system.PaymentDTOMapper;
import com.epam.jwd.service.dto.payment_system.PaymentDTO;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_DELETE_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_DELETE_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_ALL_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_BY_ID_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_SAVE_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_SAVE_METHOD_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_UPDATE_METHOD_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SERVICE_UPDATE_METHOD_EXCEPTION_CODE;

public class PaymentService implements com.epam.jwd.service.api.PaymentService<PaymentDTO, Integer> {

    private final PaymentDAO<Payment, Integer> paymentDAO;
    private final DTOMapper<PaymentDTO, Payment, Integer> mapper;

    private static final Logger log = LogManager.getLogger(PaymentService.class);

    public PaymentService() {
        this.paymentDAO = PaymentDAOImpl.getInstance();
        this.mapper = new PaymentDTOMapper();
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) throws ServiceException {
        Payment payment = mapper.convertToEntity(paymentDTO);

        try {
            paymentDTO = mapper.convertToDTO(paymentDAO.save(payment));
        } catch (DAOException e) {
            log.error(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_SAVE_METHOD_EXCEPTION + DELIMITER + SERVICE_SAVE_METHOD_EXCEPTION_CODE, e);
        }

        return paymentDTO;
    }

    @Override
    public List<PaymentDTO> findAll() throws ServiceException {
        List<PaymentDTO> payments = new ArrayList<>();

        try {
            for (Payment payment : paymentDAO.findAll()) {
                payments.add(mapper.convertToDTO(payment));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return payments;
    }

    @Override
    public PaymentDTO findById(Integer id) throws ServiceException {
        PaymentDTO paymentDTO;

        try {
            paymentDTO = mapper.convertToDTO(paymentDAO.findById(id));
        } catch (DAOException e) {
            log.error(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_BY_ID_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE, e);
        }

        return paymentDTO;
    }

    @Override
    public PaymentDTO update(PaymentDTO paymentDTO) throws ServiceException {
        Payment payment = mapper.convertToEntity(paymentDTO);

        try {
            paymentDTO = mapper.convertToDTO(paymentDAO.update(payment));
        } catch (DAOException e) {
            log.error(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_UPDATE_METHOD_EXCEPTION + DELIMITER + SERVICE_UPDATE_METHOD_EXCEPTION_CODE, e);
        }

        return paymentDTO;
    }

    @Override
    public void delete(PaymentDTO paymentDTO) throws ServiceException {
        Payment payment = mapper.convertToEntity(paymentDTO);

        try {
            paymentDAO.delete(payment);
        } catch (DAOException e) {
            log.error(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_DELETE_METHOD_EXCEPTION + DELIMITER + SERVICE_DELETE_METHOD_EXCEPTION_CODE, e);
        }
    }

    @Override
    public List<PaymentDTO> findPaymentsByUserId(Integer id) throws ServiceException {
        List<PaymentDTO> payments = new ArrayList<>();

        try {
            for (Payment payment : paymentDAO.findAllPaymentsByUserId(id)) {
                payments.add(mapper.convertToDTO(payment));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return payments;
    }

    @Override
    public List<PaymentDTO> findPaymentsByUserIdAndPage(Integer id, int page, int numOfPayments)
            throws ServiceException {
        List<PaymentDTO> payments = new ArrayList<>();

        try {
            for (Payment payment : paymentDAO.findPaymentsByUserIdAndPageLimit(id, page, numOfPayments)) {
                payments.add(mapper.convertToDTO(payment));
            }
        } catch (DAOException e) {
            log.error(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
            throw new ServiceException(SERVICE_FIND_ALL_METHOD_EXCEPTION + DELIMITER + SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE, e);
        }

        return payments;
    }
}
