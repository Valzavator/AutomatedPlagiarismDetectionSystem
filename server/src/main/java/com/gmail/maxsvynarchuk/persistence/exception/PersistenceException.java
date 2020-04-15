package com.gmail.maxsvynarchuk.persistence.exception;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class PersistenceException extends RuntimeException {

    private static final long serialVersionUID = 8524304161616176522L;

    public PersistenceException() {
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}