package com.epam.jwd.repository.exception;

public class DeleteFromDataBaseException extends RuntimeException {
    public DeleteFromDataBaseException() {
    }

    public DeleteFromDataBaseException(String message) {
        super(message);
    }

    public DeleteFromDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteFromDataBaseException(Throwable cause) {
        super(cause);
    }

    public DeleteFromDataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
