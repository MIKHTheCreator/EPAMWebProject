package com.epam.jwd.service.currency_converter;

import com.epam.jwd.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.Assert.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RequestSenderTest {

    private RequestSender sender;

    @BeforeAll
    public void beforeAll() {
        this.sender = RequestSender.getInstance();
    }

    @Test
    void prepareSendRequest() throws ServiceException {
        System.out.println(sender.prepareSendRequest("255", "BYN", "USD"));
    }
}