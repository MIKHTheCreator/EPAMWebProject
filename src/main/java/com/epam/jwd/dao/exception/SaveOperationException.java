package com.epam.jwd.dao.exception;

public class SaveOperationException extends RuntimeException {

    public SaveOperationException() {
    }

    public SaveOperationException(String message) {
        super(message);
    }

    public SaveOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveOperationException(Throwable cause) {
        super(cause);
    }

    public SaveOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
