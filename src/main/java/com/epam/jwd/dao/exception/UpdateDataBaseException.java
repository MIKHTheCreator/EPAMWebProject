package com.epam.jwd.dao.exception;

public class UpdateDataBaseException extends RuntimeException {

    public UpdateDataBaseException() {
    }

    public UpdateDataBaseException(String message) {
        super(message);
    }

    public UpdateDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateDataBaseException(Throwable cause) {
        super(cause);
    }

    public UpdateDataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
