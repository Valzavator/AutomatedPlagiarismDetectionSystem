package com.gmail.maxsvynarchuk.persistence.exception.oauth;

import com.gmail.maxsvynarchuk.persistence.exception.PersistenceException;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class OAuthIllegalTokenException extends PersistenceException {

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