package com.gmail.maxsvynarchuk.persistence.exception.oauth;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class OAuthIllegalTokenScopeException extends OAuthIllegalTokenException {

    private static final long serialVersionUID = -3730938849197722826L;

    public OAuthIllegalTokenScopeException(AccessToken accessToken) {
        super(accessToken);
    }

    public OAuthIllegalTokenScopeException(String message, AccessToken accessToken) {
        super(message, accessToken);
    }
}