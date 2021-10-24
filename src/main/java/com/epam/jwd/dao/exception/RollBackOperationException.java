package com.epam.jwd.dao.exception;

public class RollBackOperationException extends RuntimeException {

    public RollBackOperationException() {
    }

    public RollBackOperationException(String message) {
        super(message);
    }

    public RollBackOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RollBackOperationException(Throwable cause) {
        super(cause);
    }

    public RollBackOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
