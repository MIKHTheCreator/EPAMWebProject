package com.epam.jwd.service.currency_converter;

import com.epam.jwd.service.exception.ServiceException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author mikh
 * Class which provides user with option of sending the request with parameters to currency converter web-site
 * Class created by Singleton Pattern
 */
public class RequestSender {

    /**
     * @see CloseableHttpClient
     */
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static RequestSender instance = new RequestSender();
    /**
     * String Url for sending a request
     */
    private static final String XE_CONVERTER_URL = "https://www.xe.com/currencyconverter/convert/?Amount=[amount]&From=[from]&To=[to]";
    private static final String ERROR_MESSAGE = "IOException caught";

    private static final Logger log = LogManager.getLogger(RequestSender.class);

    private RequestSender() {
    }

    public static RequestSender getInstance() {
        if (instance == null) {
            instance = new RequestSender();
        }
        return instance;
    }

    /**
     * Method which rebuild seb-site URL with needed currency and amount parameters
     *
     * @param amount amount parameter
     * @param from   currency from to convert
     * @param to     currency to convert
     * @return String prepared URL
     * @throws ServiceException if it was unable to send request
     */
    public String prepareSendRequest(String amount, String from, String to)
            throws ServiceException {

        final String preparedUrl = XE_CONVERTER_URL
                .replace("[amount]", amount)
                .replace("[from]", from)
                .replace("[to]", to);


        HttpGet request = new HttpGet(preparedUrl);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity);
            }

        } catch (IOException e) {
            log.error(ERROR_MESSAGE, e);
            throw new ServiceException();
        }

        throw new ServiceException();
    }
}
