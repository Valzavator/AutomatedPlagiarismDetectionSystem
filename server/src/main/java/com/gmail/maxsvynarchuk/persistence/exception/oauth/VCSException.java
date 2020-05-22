package com.gmail.maxsvynarchuk.persistence.exception.oauth;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class VCSException extends RuntimeException {

    private static final long serialVersionUID = -147898089753392591L;

    public VCSException() {
    }

    public VCSException(String message) {
        super(message);
    }

    public VCSException(String message, Throwable cause) {
        super(message, cause);
    }

    public VCSException(Throwable cause) {
        super(cause);
    }
}