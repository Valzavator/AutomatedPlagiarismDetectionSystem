package com.autoplag.persistence.exception.oauth;

import com.autoplag.persistence.domain.vcs.AccessToken;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class OAuthIllegalTokenException extends VCSException {
    private AccessToken accessToken;

    private static final long serialVersionUID = 7176678644409382415L;

    public OAuthIllegalTokenException(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public OAuthIllegalTokenException(String message, AccessToken accessToken) {
        super(message);
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}