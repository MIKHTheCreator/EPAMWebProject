package com.epam.jwd.dao.exception;

public class FindInDataBaseException extends RuntimeException {

    public FindInDataBaseException() {
    }

    public FindInDataBaseException(String message) {
        super(message);
    }

    public FindInDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindInDataBaseException(Throwable cause) {
        super(cause);
    }

    public FindInDataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
