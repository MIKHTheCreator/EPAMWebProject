package com.epam.jwd.repository.exception;

public class FindDataBaseException extends RuntimeException {

    public FindDataBaseException() {
    }

    public FindDataBaseException(String message) {
        super(message);
    }

    public FindDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindDataBaseException(Throwable cause) {
        super(cause);
    }

    public FindDataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
