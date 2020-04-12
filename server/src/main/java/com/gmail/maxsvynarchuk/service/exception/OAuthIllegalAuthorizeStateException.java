package com.gmail.maxsvynarchuk.service.exception;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class OAuthIllegalAuthorizeStateException extends ServiceException {

    private static final long serialVersionUID = 4702457879252765486L;

    public OAuthIllegalAuthorizeStateException() {
    }

    public OAuthIllegalAuthorizeStateException(String message) {
        super(message);
    }

    public OAuthIllegalAuthorizeStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthIllegalAuthorizeStateException(Throwable cause) {
        super(cause);
    }
}