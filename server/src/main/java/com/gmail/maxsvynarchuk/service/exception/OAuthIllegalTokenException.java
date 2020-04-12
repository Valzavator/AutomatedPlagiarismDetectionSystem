package com.gmail.maxsvynarchuk.service.exception;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class OAuthIllegalTokenException extends ServiceException {

    private static final long serialVersionUID = 7176678644409382415L;

    public OAuthIllegalTokenException() {
    }

    public OAuthIllegalTokenException(String message) {
        super(message);
    }

    public OAuthIllegalTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthIllegalTokenException(Throwable cause) {
        super(cause);
    }
}