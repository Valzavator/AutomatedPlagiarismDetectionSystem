package com.gmail.maxsvynarchuk.persistence.exception.oauth;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class OAuthIllegalTokenScopeException extends OAuthIllegalTokenException {

    private static final long serialVersionUID = -3730938849197722826L;

    public OAuthIllegalTokenScopeException() {
    }

    public OAuthIllegalTokenScopeException(String message) {
        super(message);
    }

    public OAuthIllegalTokenScopeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthIllegalTokenScopeException(Throwable cause) {
        super(cause);
    }
}